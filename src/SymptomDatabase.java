import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymptomDatabase {

    enum symptomInformationList{
        SYMPTOM_NAME, SYMPTOM_FILENAME;
    }

    // root folder for all symptoms files
    private static Path symptomListFolderPath = Paths.get("database\\symptom\\symptomList");
    
    // symptomList contains the symptom name (w/in the file) and the Symptom with the symptom name as the key and the filename as the value
    private static Map<String, Symptom> symptomList = new TreeMap<String, Symptom>(String.CASE_INSENSITIVE_ORDER);

    // list of symptom names, uses the indexing functionalities of an arraylist
    private static ArrayList<String> symptomArray = new ArrayList<>();

    public static void loadFromFile(){ // reads database files
        File symptomFolder = new File(symptomListFolderPath.toAbsolutePath().toString());

        File[] symptomsFiles = symptomFolder.listFiles();

        for(File symptom: symptomsFiles){
            try (BufferedReader reader = new BufferedReader(new FileReader(symptomListFolderPath.resolve(symptom.getName()).toString()))) {
                String line = reader.readLine();
                String cleanTitle;
                String symptomName;
                Pattern pattern = Pattern.compile("::.*::");
                Matcher matcher = pattern.matcher(line);

                if(matcher.find()){
                    cleanTitle = line.trim();
                    symptomName = cleanTitle.substring(2, cleanTitle.length()-2);

                    symptomList.put(symptomName, new Symptom(symptomName, symptom.getName()));
                    symptomArray.add(symptomName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static LinkedHashMap<String, ArrayList<String>> getSymptomContents(Path symptomPath) throws NullPointerException{
        LinkedHashMap<String, ArrayList<String>> fileContents = new LinkedHashMap<>();
        
        Pattern titlePattern = Pattern.compile("::.*::");

        String cleanTitle = null;
        String title = null;
        String line = null;
     
        try (BufferedReader reader = new BufferedReader(new FileReader(symptomPath.toString()))) {
            while((line=reader.readLine()) != null && !line.equals("=====")){
                Matcher titleMatch = titlePattern.matcher(line);
                if(titleMatch.find()){
                    cleanTitle = line.trim();
                    title = cleanTitle.substring(2, cleanTitle.length()-2);
                    fileContents.put(title, new ArrayList<String>());
                } else {
                    if(!line.isEmpty()){
                        fileContents.get(title).add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }

    public static ArrayList<String> getSymptomArray(){
        return symptomArray;
    }
    
    public static Map<String, Symptom> getSymptomList(){
        return symptomList;
    }

    public static void createNewSymptom(Scanner scan){
        LinkedHashMap<String, StringBuilder> symptomContents = new LinkedHashMap<String, StringBuilder>();
        String end = "^";
        String line = null;

        System.out.println("Create New Symptom\n");
        
        System.out.print("Enter symptom name: ");
        String symptomName = scan.nextLine();
        
        symptomContents.put(symptomName, new StringBuilder());

        System.out.println("Enter definition (^ to end): ");
        symptomContents.put("Definition", new StringBuilder());

        boolean input = true;
        do{
            if(!(line = scan.nextLine()).equals(end)){
                symptomContents.get("Definition").append(line).append("\n");
            } else {
                input = false;
            }
        }while(input);

        symptomContents.put("Action/Interventions", new StringBuilder(""));

        input = true;
        do{
            System.out.print("Enter number of priorities: ");
            try{
                int prioNumber = Integer.parseInt(scan.nextLine());
                
                for(int i = 0; i < prioNumber; i++){
                    System.out.println("Enter Priority Details No. " + (i+1) + " (^ to end):");
        
                    String nursingPrio = "Nursing Priority No. " + (i+1);
                    symptomContents.put(nursingPrio, new StringBuilder());
                    boolean priority = true;
                    do{
                        line = scan.nextLine();
        
                        if(!line.equals(end)){
                            symptomContents.get(nursingPrio).append(line).append("\n");
                        } else {
                            priority = false;
                        }
                    }while(priority);
                }
                input = false;
            } catch (NumberFormatException e){
                System.out.println("\nIvalid input!\n");
            }
        } while (input);
         

        String filename = Utilities.toFilename(symptomName);
        System.out.println(filename);
        System.out.println();
        fileManagement.createSymptom(filename, symptomContents);
        SymptomDatabase.loadFromFile();        
    }

    public static Symptom getSymptom(String symptomFilename){
        String title = null;
        Symptom symptom = null;
        
        try{
            Path symptomPath = symptomListFolderPath.resolve(symptomFilename);
            try (BufferedReader reader = new BufferedReader(new FileReader(symptomPath.toString()))) {
                String line = reader.readLine();
                title = line.substring(2, line.length()-2);
    
                symptom = getSymptomList().get(title);
            } catch (IOException e) {
                System.out.println("Symptom cannot be found!");
            }
            return symptom;
        } catch (InvalidPathException e){
            return symptom;
        }
    }
}
