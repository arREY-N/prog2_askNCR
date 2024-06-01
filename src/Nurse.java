import java.util.Scanner;

public class Nurse {
    private String username;
    private String nurseName;
    private String sex;
    private int age;
    private String position;
    private String shiftSchedule;
    private String areaAssignment;
    
    public Nurse(String username, String nurseName, int age, String sex, String position, String shiftSchedule, String areaAssignment){
        this.username = username;
        this.nurseName = nurseName;
        this.age = age;
        this.sex = sex;
        this.position = position;
        this.shiftSchedule = shiftSchedule;
        this.areaAssignment = areaAssignment;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public void setShiftSchedule(String shiftSchedule){
        this.shiftSchedule = shiftSchedule;
    }

    public void setAreaAssignment(String areaAssignment){
        this.areaAssignment = areaAssignment;
    }

    public String getUsername(){
        return username;
    }

    public String getNurseName(){
        return nurseName;
    }

    public int getAge(){
        return age;
    }

    public String getSex(){
        return sex;
    }

    public String getPosition(){
        return position;
    }

    public String getShiftSchedule(){
        return shiftSchedule;
    }

    public String getAreaAssignment(){
        return areaAssignment;
    }

    public static void nurseInformationPage(Scanner scan, Nurse nurse) {
        System.out.println("NURSE INFORMATION");
        System.out.println("Name: " + nurse.getNurseName());
        System.out.println("Age: " + nurse.getAge());
        System.out.println("Sex: " + nurse.getSex());
        System.out.println("Position: " + nurse.getPosition());
        System.out.println("Shift Schedule: " + nurse.getShiftSchedule());
        System.out.println("Area Assignment: " + nurse.getAreaAssignment());
        System.out.print("\nEnter to continue...");
        scan.nextLine();
        System.out.println();
    }
}
