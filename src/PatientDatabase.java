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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PatientDatabase {

    enum patientInformationList{
        PATIENTID, NAME, AGE, SEX, DIAGNOSIS, SYMPTOM;
    }

    private static Map<String, Patient> patientList = new TreeMap<String, Patient>();
    private static Path nursePatientsPath = Paths.get("database\\nurse");
    private static Map<String, TreeMap<String, Patient>> nursePatients = new TreeMap<>();
    private static Map<String, String> patientID = new TreeMap<>();
    
    public static void loadFromFile(){
        Path patientListPath = nursePatientsPath.resolve("patientList.txt");
        Pattern nurseNamePattern = Pattern.compile("\\[.*\\]");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(patientListPath.toString()))) {
            String delimiter = "\\^";
            String line;
            String[] information = new String[6];
            ArrayList<String> symptomList = new ArrayList<String>();
            String nurseID = null;

            while((line=reader.readLine()) != null){
                Matcher nurseMatcher = nurseNamePattern.matcher(line);
    
                if(nurseMatcher.find()){
                    nurseID = line.substring(1, line.length()-1);
                    nursePatients.put(nurseID, new TreeMap<String, Patient>());
                } else {
                    information = line.split(delimiter);
                    String[] symptomLine = information[5].split(">"); 
                    if(!symptomLine[0].trim().isEmpty()){
                        symptomList = new ArrayList<String>(Arrays.asList(symptomLine));
                    } else {
                        symptomList = new ArrayList<String>();
                    }
                
                    patientID.put(
                        information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                        nurseID);

                    nursePatients.get(nurseID).put(
                        information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                        new Patient(
                            information[patientInformationList.valueOf("PATIENTID").ordinal()], 
                            information[patientInformationList.valueOf("NAME").ordinal()], 
                            Integer.parseInt(information[patientInformationList.valueOf("AGE").ordinal()]), 
                            information[patientInformationList.valueOf("SEX").ordinal()], 
                            information[patientInformationList.valueOf("DIAGNOSIS").ordinal()],
                            symptomList)
                            );
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
                    writer.write("^");
                    writer.write(patient.getValue().getName());
                    writer.write("^");
                    writer.write(String.valueOf(patient.getValue().getAge()));
                    writer.write("^");
                    writer.write(patient.getValue().getSex());
                    writer.write("^");

                    if(patient.getValue().getDiagnosisName().equals(" ")){
                        writer.write(" ");
                        writer.write("^");
                        writer.write(" ");
                        writer.write(">");
                        writer.write(" ");
                    } else {
                        writer.write(patient.getValue().getDiagnosisName());
                        writer.write("^");
                        
                        ArrayList<String> symptomFilelist = patient.getValue().getSymptomFileList();

                        if(symptomFilelist.isEmpty()){
                            writer.write(" ");
                        } else{ 
                            for(String symptom: patient.getValue().getSymptomFileList()){
                                writer.write(symptom);
                                writer.write(">");   
                            }
                        }
                    }
                    
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addNursePatientList(Nurse nurse){
        nursePatients.put(nurse.getUserID(), new TreeMap<String, Patient>());
    }

    public static Map<String, Patient> getPatientList(Nurse nurse){
        patientList = nursePatients.get(nurse.getUserID());
        return patientList;
    }
}