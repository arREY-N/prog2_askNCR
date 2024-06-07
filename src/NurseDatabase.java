import java.io.BufferedReader;
import java.io.BufferedWriter;
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
        USERID, NURSENAME, AGE, SEX, POSITION, SHIFTSCHEDULE, AREAASSIGNMENT;
    }

    private static Map<String, Nurse> nurseInformation = new TreeMap<>();
    private static Path nurseInformationPath = Paths.get("database\\nurse\\nurseInformation.txt");
    
    public static void loadFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(nurseInformationPath.toAbsolutePath().toString()))) {
            String delimiter = ",";
            String line;
            String[] information = new String[7];
            while((line = reader.readLine()) != null){
                information = line.split(delimiter);
                nurseInformation.put(
                    information[nurseInformationList.valueOf("USERID").ordinal()], 
                    new Nurse(
                        information[nurseInformationList.valueOf("USERID").ordinal()], 
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
            for(Map.Entry<String, Nurse> nurse: nurseInformation.entrySet()){
                writer.write(nurse.getValue().getUserID());
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

    public static void createNurseObject(Scanner scan, String nurseID){
        String nurseName;
        int age = 0;
        String sex;
        String position;
        String shiftSchedule;
        String areaAssignment;

        boolean invalid = true;        
        do{
            System.out.print("Name: ");
            nurseName = scan.nextLine();

            if(!nurseName.trim().isEmpty()){
                invalid = false;
            } else {
                System.out.println("\nInvalid Input!\n");
            }
        } while (invalid);

        invalid = true;        
        do{
            System.out.print("Age: ");
            try{
                age = Integer.parseInt(scan.nextLine());
                invalid = false;
            } catch (NumberFormatException e){
                System.out.println("\nInvalid Input!\n");
            }
        } while (invalid);

        invalid = true;        
        do{
            System.out.print("Sex (M/F): ");
            sex = scan.nextLine();

            if(!nurseName.trim().isEmpty()){
                invalid = false;
            } else {
                System.out.println("\nInvalid Input!\n");
            }
        } while (invalid);
        
        invalid = true;        
        do{
            System.out.print("Position: ");
            position = scan.nextLine();

            if(!nurseName.trim().isEmpty()){
                invalid = false;
            } else {
                System.out.println("\nInvalid Input!\n");
            }
        } while (invalid);

        invalid = true;        
        do{
            System.out.print("Schedule (AM/PM/NIGHT): ");
            shiftSchedule = scan.nextLine();

            if(!nurseName.trim().isEmpty()){
                invalid = false;
            } else {
                System.out.println("\nInvalid Input!\n");
            }
        } while (invalid);

        invalid = true;        
        do{
            System.out.print("Area Assignment: ");
            areaAssignment = scan.nextLine();

            if(!nurseName.trim().isEmpty()){
                invalid = false;
            } else {
                System.out.println("\nInvalid Input!\n");
            }
        } while (invalid);

        addNurseAccount(nurseID, new Nurse(nurseID, nurseName, age, sex, position, shiftSchedule, areaAssignment));
        
        PatientDatabase.addNursePatientList(nurseInformation.get(nurseID));
    }

    public static Map<String, Nurse> getNurseInformation(){
        return nurseInformation;
    }

    public static void addNurseAccount(String nurseID, Nurse nurse){
        nurseInformation.put(nurseID, nurse);
    }
}