import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

public class PatientDatabase {

    enum patientInformationList{
        PATIENTID, NAME, AGE, SEX, DIAGNOSIS;
    }

    private static Map<String, Patient> patientList;
    private static Path nursePatientsPath = Paths.get("database\\nurse");
    private static Map<String, TreeMap<String, Patient>> nursePatients = new TreeMap<>();
    private static Map<String, String> patientID = new TreeMap<>();
    
    public static void loadFromFile(){
        Path patientListPath = nursePatientsPath.resolve("patientList.txt");
        Pattern nurseNamePattern = Pattern.compile("\\[.*\\]");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(patientListPath.toString()))) {
            String delimiter = ",";
            String line;
            String[] information = new String[5];
            String nurseID = null;

            while((line=reader.readLine()) != null){
                Matcher nurseMatcher = nurseNamePattern.matcher(line);
                
                if(nurseMatcher.find()){
                    nurseID = line.substring(1, line.length()-1);
                    nursePatients.put(nurseID, new TreeMap<String, Patient>());
                } else {
                    information = line.split(delimiter);
                    patientID.put(information[patientInformationList.valueOf("PATIENTID").ordinal()], nurseID);
                    nursePatients.get(nurseID).put(
                        information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                        new Patient(
                            information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                            information[patientInformationList.valueOf("NAME").ordinal()], 
                            Integer.parseInt(information[patientInformationList.valueOf("AGE").ordinal()]), 
                            information[patientInformationList.valueOf("SEX").ordinal()], 
                            information[patientInformationList.valueOf("DIAGNOSIS").ordinal()]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getPatientID(){
        return patientID;
    }
    public static Map<String, TreeMap<String, Patient>> getNursePatients(){
        return nursePatients;
    }
    
    public static void loadToFile(){
        Path patientListPath = nursePatientsPath.resolve("patientList.txt");
        
        try (BufferedWriter writer = Files.newBufferedWriter(patientListPath)) {
            for(Map.Entry<String, TreeMap<String, Patient>> nurseCodes: nursePatients.entrySet()){
                writer.write("[");
                writer.write(nurseCodes.getKey());
                writer.write("]");
                writer.write("\n");
                
                for(Map.Entry<String, Patient> patient: nurseCodes.getValue().entrySet()){
                    writer.write(patient.getValue().getPatientId());
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addNursePatientList(Nurse nurse){
        /* 
            TreeMap object
            key() = nurseID
            value() = Patient ID, Patient
        */
        nursePatients.put(nurse.getUserID(), new TreeMap<String, Patient>());
    }

    public static Map<String, Patient> getPatientList(Nurse nurse){
        patientList = nursePatients.get(nurse.getUserID());
        return patientList;
    }
}