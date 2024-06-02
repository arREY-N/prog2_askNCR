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

    public static void removeFolder(Path folderPath, String userID){
        
    }

    public static void removeFile(String fileName, Path folderPath){
        
    }
}
