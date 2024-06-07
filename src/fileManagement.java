import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class fileManagement {
    public static void createFolder(Path folderPath, String userID) throws FolderCreationException{
        Path userFolderPath = folderPath.resolve(userID);
        File folderFile = new File(userFolderPath.toString());
        
        if(folderFile.mkdir()){
            System.out.println();
            System.out.println(userID + " folder created!\n");           
        } else {
            throw new FolderCreationException();
        }
    }

    public static void createFile(String filename, Path filePath) throws IOException{
        File userFile = new File(filePath.resolve(filename).toString());
        try {
            userFile.createNewFile();
            System.out.println(filename + " created!\n");
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public static void createSymptom(String filename, LinkedHashMap<String, StringBuilder> fileContents){
        Path symptomPath = Paths.get("database\\symptom\\symptomList");
        try {
            createFile(filename, symptomPath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(symptomPath.resolve(filename).toString()))) {
                for(Map.Entry<String, StringBuilder> content: fileContents.entrySet()){
                    Pattern nursingPrio = Pattern.compile("Nursing Priority No\\. \\d");
                    Matcher match = nursingPrio.matcher(content.getKey());
                    
                    if(match.find()){
                        writer.write("[");
                        writer.write(content.getKey());
                        writer.write("]");
                        writer.write("\n");
                        writer.write(content.getValue().toString());
                        writer.write("\n");
                    } else {
                        writer.write("::");
                        writer.write(content.getKey());
                        writer.write("::");
                        writer.write("\n");
                        writer.write(content.getValue().toString());
                        writer.write("\n");
                    }
                }
                writer.write("=====\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDiagnosis(String filename, LinkedHashMap<String, ArrayList<String>> fileContents){
        Path diagnosisPath = Paths.get("database\\diagnosis\\diagnosisList");
        try {
            createFile(filename, diagnosisPath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(diagnosisPath.resolve(filename).toString()))) {
                for(Map.Entry<String, ArrayList<String>> content: fileContents.entrySet()){
                    Pattern nursingPrio = Pattern.compile("Symptoms");
                    Matcher match = nursingPrio.matcher(content.getKey());
                    
                    if(match.find()){
                        for(String symptom: content.getValue()){
                            String symptomFile = Utilities.toFilename(symptom); 
                            writer.write(symptom);
                            writer.write(">");
                            writer.write(symptomFile);
                            writer.write("\n");
                        }
                    } else {
                        writer.write("::");
                        writer.write(content.getKey());
                        writer.write("::");
                        writer.write("\n");
                    }
                }
                writer.write("=====\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFolder(Path folderPath){
        System.out.println(folderPath.toString());
        File userFolder = new File(folderPath.toString());
        File[] userFiles = userFolder.listFiles();

        if(userFiles != null && userFiles.length > 0){
            for(File file: userFiles){
                file.delete();
            }
        } else {
            userFolder.delete();
        }
    }

    public static void createCareFile(ArrayList<LinkedHashMap<String, ArrayList<String>>> careRecommendations, Patient patient, Nurse nurse){
        LocalDateTime creationTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm");
        String patientId = patient.getPatientId();
        String nurseId = nurse.getUserID();

        Path filePath = Paths.get("database\\nurse\\" + nurseId + "\\" + patientId);
        
        String filename = patient.getPatientId() +"_" + creationTime.format(format) + ".txt";
        
        try {
            createFile(filename, filePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.resolve(filename).toString()))) {
                for(LinkedHashMap<String, ArrayList<String>> symptom: careRecommendations){
                    
                    for(Map.Entry<String, ArrayList<String>> content : symptom.entrySet()){
                        writer.write("::" + content.getKey() + "::");
                        writer.write("\n");
                        for(String texts: content.getValue()){
                            writer.write(texts);
                            writer.write("\n");           
                        }
                        writer.write("\n");
                    }
                    writer.write("================================================================\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}