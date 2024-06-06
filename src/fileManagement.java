import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class fileManagement {
    public static void createFolder(Path folderPath, String userID) throws FolderCreationException{
        Path userFolderPath = folderPath.resolve(userID);
        File folderFile = new File(userFolderPath.toString());
        
        if(folderFile.mkdir()){
            System.out.println();
            System.out.println(userID + " folder created!");           
        } else {
            throw new FolderCreationException();
        }
    }

    public static void createFile(String filename, Path filePath) throws IOException{
        File userFile = new File(filePath.resolve(filename).toString());
        try {
            userFile.createNewFile();
            System.out.println(filename + " created!");
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public static void removeFolder(Path folderPath){
        System.out.println(folderPath.toString());
        File userFolder = new File(folderPath.toString());
        File[] userFiles = userFolder.listFiles();

        if(userFiles != null && userFiles.length > 0){
            System.out.println("userFiles != null && userFiles.length > 0");
            for(File file: userFiles){
                file.delete();
            }
        } else {
            System.out.println("deleting folder");
            userFolder.delete();
        }
    }

    public static void removeFile(String fileName, Path folderPath){
        
    }

    public static void createCareFile(ArrayList<LinkedHashMap<String, ArrayList<String>>> careRecommendations, Patient patient, Nurse nurse){
        Scanner scan = new Scanner(System.in);
        LocalDateTime creationTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm");
        String patientId = patient.getPatientId();
        String nurseId = nurse.getUserID();

        Path filePath = Paths.get("database\\nurse\\" + nurseId + "\\" + patientId);
        
        String filename = patient.getPatientId() +"_" + creationTime.format(format) + ".txt";

        System.out.println("Filepath: " + filePath.toString());
        System.out.println("Filename: " + filename);
        System.out.println("Location: " + filePath.resolve(filename).toString());
        scan.nextLine();

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
