import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Map;

public class DiagnosesDatabase {

    enum diagnosisInformationList{
        DIAGNOSIS_NAME, DIAGNOSIS_FILENAME;
    }

    private static Map<Integer, Diagnosis> diagnosisNames = new TreeMap<Integer, Diagnosis>();

    private static Path diagnosisListPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\diagnosis\\diagnosisList.txt");
    private static Path diagnosisListFolderPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\diagnosis\\diagnosisList");

    public static void loadFromFile(){
        File diagnosisFolder = new File(diagnosisListFolderPath.toString());

        File[] diagnosisList = diagnosisFolder.listFiles();
        int diagnosisNumber = 1;

        for(File diagnosis: diagnosisList){
            try (BufferedReader reader = new BufferedReader(new FileReader(diagnosisListFolderPath.resolve(diagnosis.getName()).toString()))) {
                String line;
                Pattern pattern = Pattern.compile("::.*::");
                diagnosisNames.put(diagnosisNumber, new Diagnosis(null, null, new ArrayList<String>()));

                while((line=reader.readLine()) != null && !line.equals("=====")){
                    Matcher nameMatcher = pattern.matcher(line);

                    if(nameMatcher.find()){
                        String diagnosisName = line.trim().substring(2, line.length()-2);
                        diagnosisNames.get(diagnosisNumber).setDiagnosisName(diagnosisName);
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

    public static ArrayList<String> getDiagnosis(Integer diagnosisNumber) throws FileNotFoundException{
        String diagnosisFilePath = diagnosisListFolderPath.resolve(diagnosisNames.get(diagnosisNumber).getFileName()).toString();
        ArrayList<String> symptomList = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(diagnosisFilePath));
            String line;
            String[] parts = null;
            String delimiter = ">";
            String endOfDocs = "=====";
            int symptomNumber = 1;
            
            System.out.println();

            while((line=reader.readLine()) != null){    
                parts = line.split(delimiter);   
                if(parts.length>1){
                    System.out.println(symptomNumber + ". " + parts[0]);
                    symptomNumber++;
                    symptomList.add(parts[0]);
                } else if (line.equals(endOfDocs)){
                    System.out.println();
                    break;
                } else {
                    System.out.println(parts[0] + "\n");
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nThe system currently doesn't have any information about this symptom.\n");
            throw new FileNotFoundException();
        } catch (IOException e){
            System.out.println("Error reading diagnosis file");
        }

        return symptomList;
    }

    public static Map<Integer, Diagnosis> getDiagnosisList(){
        return diagnosisNames;
    }

    public static void loadToFile(){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(diagnosisListPath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Map.Entry<Integer, Diagnosis> diagnosis : diagnosisNames.entrySet()) {
                writer.write(diagnosis.getValue().getDiagnosisName());
                writer.write('>');
                writer.write(diagnosis.getValue().getFileName());
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}