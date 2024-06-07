import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

public class DiagnosesDatabase {

    enum diagnosisInformationList{
        DIAGNOSIS_NAME, DIAGNOSIS_FILENAME;
    }

    private static Path diagnosisListFolderPath = Paths.get("database\\diagnosis\\diagnosisList");

    private static Map<Integer, Diagnosis> diagnosisNames = new TreeMap<Integer, Diagnosis>();
    private static Map<String, Integer> diagnosisTable = new TreeMap<String, Integer>();

    public static void loadFromFile(){
        File diagnosisFolder = new File(diagnosisListFolderPath.toAbsolutePath().toString());

        File[] diagnosisFiles = diagnosisFolder.listFiles();
        int diagnosisNumber = 1;

        for(File diagnosis: diagnosisFiles){
            try (BufferedReader reader = new BufferedReader(new FileReader(diagnosisListFolderPath.toAbsolutePath().resolve(diagnosis.getName()).toString()))) {
                String line;
                Pattern pattern = Pattern.compile("::.*::");
                diagnosisNames.put(diagnosisNumber, new Diagnosis(null, null, new ArrayList<String>()));

                while((line=reader.readLine()) != null && !line.equals("=====")){
                    Matcher nameMatcher = pattern.matcher(line);

                    if(nameMatcher.find()){
                        String diagnosisName = line.trim().substring(2, line.length()-2);
                        diagnosisNames.get(diagnosisNumber).setDiagnosisName(diagnosisName);
                        diagnosisNames.get(diagnosisNumber).setFileName(diagnosis.getName());
                        diagnosisTable.put(diagnosisName, diagnosisNumber);
                    } else {
                        String[] symptomInfo = line.split(">");
                        diagnosisNames.get(diagnosisNumber).addSymptom(symptomInfo[0]);
                    }
                }
                diagnosisNumber++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getDiagnosisContents(Path diagnosisFilePath){
        
        ArrayList<String> diagnosisContents = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(diagnosisFilePath.toString()))){
            String line;
            String[] parts = null;
            String delimiter = ">";
            String endOfDocs = "=====";

            while((line=reader.readLine()) != null){    
                parts = line.split(delimiter);   
                if(parts.length>1){
                    diagnosisContents.add(parts[0]);
                } else if (line.equals(endOfDocs)){
                    break;
                } else {
                    continue;
                }
            }
        } catch (IOException e){
            System.out.println("Error reading diagnosis contents!");
        }

        return diagnosisContents;
    }

    public static Map<String, Integer> getDiagnosisTable(){
        return diagnosisTable;
    }

    public static Map<Integer, Diagnosis> getDiagnosisList(){
        return diagnosisNames;
    }

    public static void createNewDiagnosis(Scanner scan){
        LinkedHashMap<String, ArrayList<String>> diagnosisContents = new LinkedHashMap<String, ArrayList<String>>();
        String end = "^";
        String line = null;
        String diagnosisName = null;

        System.out.println("Create New Diagnosis\n");
        boolean invalid = true;
        do{
            System.out.print("Enter Diagnosis name: ");
            diagnosisName = scan.nextLine();
            if(!diagnosisName.trim().isEmpty()){
                diagnosisContents.put(diagnosisName, new ArrayList<String>());
                invalid = false;
            }
        } while (invalid);
        
        
        System.out.println("Enter symptoms (^ to end): ");
        diagnosisContents.put("Symptoms", new ArrayList<String>());

        boolean input = true;
        do{
            if(!(line = scan.nextLine()).equals(end)){
                diagnosisContents.get("Symptoms").add(line);
            } else {
                input = false;
            }
        }while(input);

        String filename = Utilities.toFilename(diagnosisName);

        fileManagement.createDiagnosis(filename, diagnosisContents);        
    }
}