import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class PatientDatabase {

    enum nurseInformationList{
        PATIENTID, NAME, AGE, SEX, DIAGNOSIS, CARERECOMMENDATION;
    }

    private static Map<String, Patient> patientList = new HashMap<>();
    private static Path patientPath = Paths.get("database\\patient\\patientInformation.txt");

    public static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(patientPath.toAbsolutePath().toString()))) {
            String delimiter = ",";
            String line;
            String[] information = new String[6];

            while ((line = reader.readLine()) != null) {
                information = line.split(delimiter);
                patientList.put(
                    information[0],
                    new Patient(
                        information[nurseInformationList.valueOf("PATIENTID").ordinal()], 
                        information[nurseInformationList.valueOf("NAME").ordinal()], 
                        Integer.parseInt(information[nurseInformationList.valueOf("AGE").ordinal()]),
                        information[nurseInformationList.valueOf("SEX").ordinal()],
                        information[nurseInformationList.valueOf("DIAGNOSIS").ordinal()], 
                        information[nurseInformationList.valueOf("CARERECOMMENDATION").ordinal()] 
                    )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(patientPath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Map.Entry<String, Patient> patient : patientList.entrySet()) {
                writer.write(patient.getKey());
                writer.write(',');
                writer.write(patient.getValue().getName());
                writer.write(',');
                writer.write(String.valueOf(patient.getValue().getAge()));
                writer.write(',');
                writer.write(patient.getValue().getSex());
                writer.write(',');
                writer.write(patient.getValue().getDiagnosis());
                writer.write(',');
                writer.write(patient.getValue().getCareRecommendation());
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Patient> getPatientAccounts() {
        return patientList;
    }

    public static void addPatientAccount(String patientId, Patient patient) {
        patientList.put(patientId, patient);
    }
    
    // public static void showPatients() {
    //     System.out.println("\nPatient Database\n");
    //     for (Map.Entry<String, Patient> patient : patientList.entrySet()) {
    //         System.out.println("Patient ID: " + patient.getKey());
    //         System.out.println("Name: " + patient.getValue().getName());
    //         System.out.println("Age: " + patient.getValue().getAge());
    //         System.out.println("Sex: " + patient.getValue().getSex());
    //         System.out.println("Diagnosis: " + patient.getValue().getDiagnosis());
    //         System.out.println("Care Recommendation: " + patient.getValue().getCareRecommendation());
    //         System.out.println();
    //     }
    // }
    // 
    // public static Patient getPatient(String patientId) {
    //     return patientList.get(patientId);
    // }
}