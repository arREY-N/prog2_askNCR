import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.TreeMap;
import java.util.Map;
import java.io.FileReader;

public class NurseDatabase{

    enum nurseInformationList{
        USERNAME, NURSENAME, AGE, SEX, POSITION, SHIFTSCHEDULE, AREAASSIGNMENT, PATIENTFOLDER;
    }

    private static Map<String, Nurse> nurseList = new TreeMap<>();
    private static Path nursePath = Paths.get("database\\nurse\\nurseInformation.txt");

    public static void loadFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(nursePath.toAbsolutePath().toString()))) {
            String delimiter = ",";
            String line;
            String[] information = new String[8];

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
                        information[nurseInformationList.valueOf("AREAASSIGNMENT").ordinal()], 
                        information[nurseInformationList.valueOf("PATIENTFOLDER").ordinal()]
                    )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadToFile(){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nursePath.toAbsolutePath().toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
            for(Map.Entry<String, Nurse> nurse: nurseList.entrySet()){
                writer.write(nurse.getKey());
                writer.write(',');
                writer.write(nurse.getValue().getUsername());
                writer.write(',');
                writer.write(nurse.getValue().getNurseName());
                writer.write(',');
                writer.write(nurse.getValue().getAge());
                writer.write(',');
                writer.write(nurse.getValue().getSex());
                writer.write(',');
                writer.write(nurse.getValue().getPosition());
                writer.write(',');
                writer.write(nurse.getValue().getShiftSchedule());
                writer.write(',');
                writer.write(nurse.getValue().getAreaAssignment());
                writer.write(',');
                writer.write(nurse.getValue().getPatientFolder());
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TreeMap<String, Nurse> getNurseAccounts(){
        return (TreeMap<String, Nurse>) nurseList;
    }

    public static void addNurseAccount(String username, Nurse nurse){
        nurseList.put(username, nurse);
    }

}