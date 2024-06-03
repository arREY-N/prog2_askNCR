import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
        File nurseFile = new File(filePath.resolve(filename).toString());
        try {
            nurseFile.createNewFile();
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
}
