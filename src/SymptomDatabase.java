import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymptomDatabase {

    enum symptomInformationList{
        SYMPTOM_NAME, SYMPTOM_FILENAME;
    }

    private static Map<String, String> symptomList = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

    private static Path symptomListFolderPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\symptom\\symptomList");

    public static void loadFromFile(){
        File symptomFolder = new File(symptomListFolderPath.toString());

        File[] symptomsList = symptomFolder.listFiles();

        for(File symptom: symptomsList){
            try (BufferedReader reader = new BufferedReader(new FileReader(symptomListFolderPath.resolve(symptom.getName()).toString()))) {
                String line = reader.readLine();
                
                Pattern pattern = Pattern.compile("::.*::");
                Matcher matcher = pattern.matcher(line);

                if(matcher.find()){
                    symptomList.put((line.substring(2, line.length()-2)), symptom.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showSymptoms(){
        System.out.println("\nSymptoms Database\n");
        for(Map.Entry<String, String> symptom: symptomList.entrySet()){
            System.out.println(symptom.getKey() + ". " + symptom.getValue());
        }
    }

    public static void getSymptom(String symptomName){
        System.out.println();
        Scanner scan = new Scanner(System.in);
        try{
            Path symptomPath = symptomListFolderPath.resolve(symptomList.get(symptomName));
            Map<String, StringBuilder> symptomContents = new TreeMap<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(symptomPath.toString()))) {
                String line;
                String endOfDocs = "=====";
                String partTitle = null;
                Pattern fileParts = Pattern.compile("::.*::");
                Pattern nursingPrioParts = Pattern.compile("[Nursing Priority No. 0-9*]");
    
                while((line=reader.readLine()) != null && !line.trim().isEmpty() && !line.equals(endOfDocs)){
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
                            Matcher prioMatch = nursingPrioParts.matcher(line);
    
                            while((line=reader.readLine()) != null && !line.trim().isEmpty() && !line.equals(endOfDocs)){
                                if(prioMatch.find()){
                                    String nursingPrio = line.trim();
                                    symptomContents.put(nursingPrio, new StringBuilder(""));
    
                                    while((line=reader.readLine()) != null && !line.trim().isEmpty() && !line.equals(endOfDocs)){
                                        symptomContents.get(nursingPrio).append(line).append("\n");    
                                    }
    
                                    System.out.println(nursingPrio);
                                    System.out.println(symptomContents.get(nursingPrio).toString());
    
                                    if(!line.equals(endOfDocs)){
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
        }
        
        
    }
}
