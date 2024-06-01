import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;

public class NurseDatabase{

    enum nurseInformationList{
        USERNAME, NURSENAME, AGE, SEX, POSITION, SHIFTSCHEDULE, AREAASSIGNMENT;
    }

    private static Map<String, Nurse> nurseList = new TreeMap<>();
    private static Path nurseInformationPath = Paths.get("database\\nurse\\nurseInformation.txt");
    private static Path nurseFolder = Paths.get("database\\nurse");

    public static void loadFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(nurseInformationPath.toAbsolutePath().toString()))) {
            String delimiter = ",";
            String line;
            String[] information = new String[7];
            while((line = reader.readLine()) != null){
                information = line.split(delimiter);
                nurseList.put(
                    information[0], 
                    new Nurse(
                        information[nurseInformationList.valueOf("USERNAME").ordinal()], 
                        information[nurseInformationList.valueOf("NURSENAME").ordinal()], 
                        Integer.parseInt(information[nurseInformationList.valueOf("AGE").ordinal()]),
                        information[nurseInformationList.valueOf("SEX").ordinal()],
                        information[nurseInformationList.valueOf("POSITION").ordinal()], 
                        information[nurseInformationList.valueOf("SHIFTSCHEDULE").ordinal()], 
                        information[nurseInformationList.valueOf("AREAASSIGNMENT").ordinal()]
                    )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadToFile(){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nurseInformationPath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for(Map.Entry<String, Nurse> nurse: nurseList.entrySet()){
                writer.write(nurse.getValue().getUsername());
                writer.write(',');
                writer.write(nurse.getValue().getNurseName());
                writer.write(',');
                writer.write(String.valueOf(nurse.getValue().getAge()));
                writer.write(',');
                writer.write(nurse.getValue().getSex());
                writer.write(',');
                writer.write(nurse.getValue().getPosition());
                writer.write(',');
                writer.write(nurse.getValue().getShiftSchedule());
                writer.write(',');
                writer.write(nurse.getValue().getAreaAssignment());
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createNurseObject(Scanner scan, String username){
        System.out.println("\nEnter Nursing Credentials!");
        System.out.print("Name: ");
        String nurseName = scan.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scan.nextLine());
        System.out.print("Sex: ");
        String sex = scan.nextLine();
        System.out.print("Position: ");
        String position = scan.nextLine();
        System.out.print("Shift Schedule: ");
        String shiftSchedule = scan.nextLine();
        System.out.print("Area Assignment: ");
        String areaAssignment = scan.nextLine();

        nurseList.put(username, new Nurse(username, nurseName, age, sex, position, shiftSchedule, areaAssignment));
    }

    public static void createNurseFolder(String username) throws FolderCreationException{
        Path nurseFolderPath = nurseFolder.resolve(username);
        File folderPath = new File(nurseFolderPath.toString());
        
        if(folderPath.mkdir()){
            System.out.println();
            System.out.println(username + " folder created!");

            String nurseFilename = username + "_patients.txt";
            File nurseFile = new File(nurseFolderPath.resolve(nurseFilename).toString());

            try {
                nurseFile.createNewFile();
                System.out.println(nurseFilename + " created!");
            } catch (IOException e) {
                e.printStackTrace();
            }
                
        } else {
            throw new FolderCreationException();
        }
    }

    public static void createNurseFile(String username) {

    }

    public static Map<String, Nurse> getNurseAccounts(){
        return nurseList;
    }

    public static void addNurseAccount(String username, Nurse nurse){
        nurseList.put(username, nurse);
    }
}