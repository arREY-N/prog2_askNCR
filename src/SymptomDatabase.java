import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public static LinkedHashMap<String, ArrayList<String>> getSymptom(String symptomName) throws NullPointerException{
        System.out.println();
        LinkedHashMap<String, ArrayList<String>> fileContents = new LinkedHashMap<>();
        try{
            Path symptomPath = symptomListFolderPath.resolve(symptomList.get(symptomName).getFileName());

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
        } catch (NullPointerException npe){
            throw new NullPointerException();
        } 
        return fileContents;
    }

    public static void createNewSymptom(Scanner scan){
        System.out.println("\nCreate New Symptom\n");
        scan.nextLine();

    }
}
