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
    private static Path nursePatientsPath = Paths.get("database\\nurse");
        
    public static void loadNursePatients(Nurse nurse){
        Path nursePatientPath = nursePatientsPath.resolve(nurse.getUsername()).resolve(nurse.getUsername() + "_patients.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(nursePatientPath.toAbsolutePath().toString()))) {
            String delimiter = ",";
            String line;
            String[] information = new String[6];

            while((line=reader.readLine()) != null){
                information = line.split(delimiter);
                patientList.put(
                    information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                    new Patient(
                        information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                        information[patientInformationList.valueOf("NAME").ordinal()], 
                        Integer.parseInt(information[patientInformationList.valueOf("AGE").ordinal()]), 
                        information[patientInformationList.valueOf("SEX").ordinal()], 
                        information[patientInformationList.valueOf("DIAGNOSIS").ordinal()]
                    )
                );
            }
        } catch (IOException e) {
            System.out.println("Patient List cannot be found!");
        }
    }

    public static void loadToFile(Nurse nurse){
        Path nursePatientPath = nursePatientsPath.resolve(nurse.getUsername()).resolve(nurse.getUsername() + "_patients.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nursePatientPath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for(Map.Entry<String, Patient> patient : patientList.entrySet()){
                writer.write(patient.getKey());
                writer.write(",");
                writer.write(patient.getValue().getName());
                writer.write(",");
                writer.write(String.valueOf(patient.getValue().getAge()));
                writer.write(",");
                writer.write(patient.getValue().getSex());
                writer.write(",");
                writer.write(patient.getValue().getDiagnosis());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Patient> getPatientList(){
        return patientList;
    }
}