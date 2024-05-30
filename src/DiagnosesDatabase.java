import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

    private static Path diagnosisPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\diagnosis\\diagnosisList.txt");
    private static Path diagnosisFolderPath = Paths.get("C:\\Users\\user\\OneDrive\\Desktop\\Computer Programming 2\\askNCR\\database\\symptom\\symptomList");

    public static void loadFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(diagnosisPath.toAbsolutePath().toString()))){
            String delimiter = ">";
            String endOfDocs = "=====";
            String line;
            String[] diagnosisInformation = null;
            int diagnosisNumber = 1;

            while(!(line = reader.readLine()).equals(endOfDocs)){
                diagnosisInformation = line.split(delimiter);
                diagnosesList.put(
                    diagnosisNumber, 
                    new Diagnosis(diagnosisInformation[0], diagnosisInformation[1])
                );
                diagnosisNumber++;
            }
        } catch (IOException e){
            System.out.println("\nDiagnoses List Not Found!\n");
        }
    }

    public static void loadToFile(){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(diagnosisPath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Map.Entry<Integer, Diagnosis> account : diagnosesList.entrySet()) {
                writer.write(account.getKey());
                writer.write(',');
                writer.write(account.getValue().getDiagnosisName());
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

    public static void getDiagnosis(Integer diagnosisNumber){
        String diagnosisFilePath = diagnosisFolderPath.resolve(diagnosesList.get(diagnosisNumber).getFileName()).toString();
        readDiagnosis(diagnosisFilePath);
    }

    public static void readDiagnosis(String diagnosisFile){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(diagnosisFile));
            String line;

            while((line=reader.readLine()) != null){
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
