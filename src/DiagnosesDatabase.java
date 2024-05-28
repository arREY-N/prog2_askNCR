import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class DiagnosesDatabase {

    enum diagnosisInformationList{
        DIAGNOSIS_NAME, DIAGNOSIS_FILENAME;
    }

    private static Map<Integer, Diagnosis> diagnosesList = new TreeMap<>();

    private static Path diagnosisListPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\diagnosis\\diagnosisList.txt");
    private static Path diagnosisListFolderPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\diagnosis\\diagnosisList");

    public static void loadFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(diagnosisListPath.toAbsolutePath().toString()))){
            String delimiter = ">";
            String endOfDocs = "=====";
            String line;
            String[] diagnosisInformation = null;
            int diagnosisNumber = 1;

            while(!(line = reader.readLine()).equals(endOfDocs)){
                diagnosisInformation = line.split(delimiter);
                diagnosesList.put(
                    diagnosisNumber, 
                    new Diagnosis(
                        diagnosisInformation[diagnosisInformationList.DIAGNOSIS_NAME.ordinal()], 
                        diagnosisInformation[diagnosisInformationList.DIAGNOSIS_FILENAME.ordinal()])
                    );
                diagnosisNumber++;
            }
        } catch (IOException e){
            System.out.println("\nDiagnoses List Not Found!\n");
        }
    }

    public static void loadToFile(){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(diagnosisListPath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Map.Entry<Integer, Diagnosis> diagnosis : diagnosesList.entrySet()) {
                writer.write(diagnosis.getValue().getDiagnosisName());
                writer.write(',');
                writer.write(diagnosis.getValue().getFileName());
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDiagnoses(){
        System.out.println("\nDiagnoses Database\n");
        for(Map.Entry<Integer, Diagnosis> diagnosis: diagnosesList.entrySet()){
            System.out.println(diagnosis.getKey() + ". " + diagnosis.getValue().getDiagnosisName());
        }
    }

    public static void getDiagnosis(Integer diagnosisNumber) throws FileNotFoundException{
        String diagnosisFilePath = diagnosisListFolderPath.resolve(diagnosesList.get(diagnosisNumber).getFileName()).toString();

        readDiagnosis(diagnosisFilePath);
    }

    public static void readDiagnosis(String diagnosisFile) throws FileNotFoundException{
        try {
            BufferedReader reader = new BufferedReader(new FileReader(diagnosisFile));
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
    }
}
