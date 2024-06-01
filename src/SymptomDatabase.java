import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymptomDatabase {

    enum symptomInformationList{
        SYMPTOM_NAME, SYMPTOM_FILENAME;
    }

    private static Map<String, Symptom> symptomList = new TreeMap<String, Symptom>(String.CASE_INSENSITIVE_ORDER);
    private static Path symptomListFolderPath = Paths.get("database\\symptom\\symptomList");
    private static ArrayList<String> symptomArray = new ArrayList<>();

    public static void loadFromFile(){
        File symptomFolder = new File(symptomListFolderPath.toAbsolutePath().toString());

        File[] symptomsList = symptomFolder.listFiles();

        for(File symptom: symptomsList){
            try (BufferedReader reader = new BufferedReader(new FileReader(symptomListFolderPath.resolve(symptom.getName()).toString()))) {
                String line = reader.readLine();
                
                Pattern pattern = Pattern.compile("::.*::");
                Matcher matcher = pattern.matcher(line);

                if(matcher.find()){
                    String symptomName = line.trim().substring(2, line.length()-2);
                    symptomList.put(symptomName, new Symptom(symptomName, symptom.getName()));
                    symptomArray.add(symptomName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getSymptomList(){
        return symptomArray;
    }  

    // edit here!!!!!
    public static void getSymptom(Scanner scan, String symptomName){
        System.out.println();

        Path symptomPath = symptomListFolderPath.resolve(symptomList.get(symptomName).getFileName());
        Map<String, StringBuilder> symptomContents = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(symptomPath.toString()))) {
            String line;
            String endOfDocs = "=====";
            String partTitle;
            Pattern fileParts = Pattern.compile("::.*::");
            Pattern nursePrioParts = Pattern.compile("\\[Nursing Priority No\\. \\d+\\]");

            while(!(line = reader.readLine()).equals(endOfDocs)){
                Matcher partsMatch = fileParts.matcher(line);

                if(partsMatch.matches()){
                    String cleanLine = line.trim();
                    partTitle = cleanLine.substring(2, cleanLine.length()-2);

                    do{
                        line = reader.readLine();
                        partsMatch = fileParts.matcher(line);
                        System.out.println(line);
                        scan.nextLine();
                    } while (!partsMatch.matches());
                } 
            }
            System.out.print("Enter to continue...");
            scan.nextLine();
        } catch (IOException e) {
            System.out.println(symptomName + " cannot be read!");
        }
        System.out.println();
    }

    public static void getSymptoms(Scanner scan, String symptomName){
        System.out.println();
        try{
            Path symptomPath = symptomListFolderPath.resolve(symptomList.get(symptomName).getFileName());
            Map<String, StringBuilder> symptomContents = new TreeMap<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(symptomPath.toString()))) {
                String line;
                String endOfDocs = "=====";
                String partTitle = null;
                Pattern fileParts = Pattern.compile("::.*::");
                Pattern nursingPrioParts = Pattern.compile("\\[Nursing Priority No\\. \\d+\\]");
    
                while((line=reader.readLine()) != null && !line.equals(endOfDocs)){
                    Matcher partsMatch = fileParts.matcher(line);
                    
                    if(partsMatch.find()){
                        partTitle = line.substring(2, line.length()-2);
    
                        if(!line.equals("::Action/Interventions::")){
                            symptomContents.put(partTitle, new StringBuilder(""));
    
                            while((line=reader.readLine()) != null && !line.trim().isEmpty()){
                                symptomContents.get(partTitle).append(line).append("\n");
                            }
    
                            System.out.println(partTitle);
                            System.out.println(symptomContents.get(partTitle).toString());
                            System.out.print("[Enter to continue]");
                            scan.nextLine();
                            System.out.println();
                        } else {
                            while((line=reader.readLine()) != null && !line.equals(endOfDocs)){
                                Matcher prioMatch = nursingPrioParts.matcher(line);
                                if(prioMatch.find()){
                                    String nursingPrio = line.trim();
                                    symptomContents.put(nursingPrio, new StringBuilder(""));
    
                                    while((line=reader.readLine()) != null && !line.isEmpty()){
                                        if(line.equals(endOfDocs)){
                                            break;    
                                        } else {
                                            symptomContents.get(nursingPrio).append(line).append("\n");
                                        }
                                    }
    
                                    System.out.println(nursingPrio);
                                    System.out.println(symptomContents.get(nursingPrio).toString());
    
                                    if(line!= null && !line.equals(endOfDocs)){
                                        System.out.print("[Enter to continue]");
                                        scan.nextLine();
                                    } else {
                                        System.out.println("--End of Document--");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException npe){
            System.out.println("File not found!");
            npe.printStackTrace();
        }
    }
}
