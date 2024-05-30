import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.TreeMap;
import java.util.Map;

public class PatientDatabase {

    enum patientInformationList{
        PATIENTID, NAME, AGE, SEX, DIAGNOSIS, CARERECOMMENDATION;
    }

    private static Map<String, Patient> patientList = new TreeMap<>();
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
                        information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                        information[patientInformationList.valueOf("NAME").ordinal()], 
                        Integer.parseInt(information[patientInformationList.valueOf("AGE").ordinal()]),
                        information[patientInformationList.valueOf("SEX").ordinal()],
                        information[patientInformationList.valueOf("DIAGNOSIS").ordinal()], 
                        information[patientInformationList.valueOf("CARERECOMMENDATION").ordinal()] 
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
                writer.write(patient.getValue().getPatientFolderPath());
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
    
    public static void removePatient(String patientid){
        patientList.remove(patientid);
    }

    public static void showPatientList() {
        System.out.println("\nPatient List:");
        for (Map.Entry<String, Patient> entry : patientList.entrySet()) {
            Patient patient = entry.getValue();
            System.out.println("- " + patient.getName());
        }
    }

    public static void showPatientsDetails(String name) {
        for (Patient patient : patientList.values()) {
            if (patient.getName().equalsIgnoreCase(name)) {
                System.out.println("\nPatient Details: ");
                System.out.println("Name: " + patient.getName());
                System.out.println("Age: " + patient.getAge());
                System.out.println("Sex: " + patient.getSex());
                System.out.println("Diagnosis: " + patient.getDiagnosis());
                System.out.println("Care Recommendation: " + patient.getPatientFolderPath());
                System.out.println();
                return;
            }
        
        }
        System.out.println("Patient not found.");
    }
    
    public static Patient getPatient(String patientId) {
        return patientList.get(patientId);
    }

    public static void addPatient(Nurse nurse, String patientId) {
        System.out.println("Patient added to nurse's list.");
    }

    public static void removePatient(Nurse nurse, String patientId) {
        System.out.println("Patient added to nurse's list.");
    }
}