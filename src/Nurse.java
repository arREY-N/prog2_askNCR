public class Nurse {
    private static String username;
    private static String nurseName;
    private static String sex;
    private static int age;
    private static String position;
    private static String shiftSchedule;
    private static String areaAssignment;
    private String patientFolder;
    
    public Nurse(String username, String nurseName, int age, String sex, String position, String shiftSchedule, String areaAssignment, String patientFolder){
        this.username = username;
        this.nurseName = nurseName;
        this.age = age;
        this.position = position;
        this.shiftSchedule = shiftSchedule;
        this.areaAssignment = areaAssignment;
        this.patientFolder = patientFolder;
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

    public void setPatientFolder(String patientFolder){
        this.patientFolder = patientFolder;
    }

    public static String getUsername(){
        return username;
    }

    public static String getNurseName(){
        return nurseName;
    }

    public static int getAge(){
        return age;
    }

    public static String getSex(){
        return sex;
    }

    public static String getPosition(){
        return position;
    }

    public static String getShiftSchedule(){
        return shiftSchedule;
    }

    public static String getAreaAssignment(){
        return areaAssignment;
    }

    public String getPatientFolder(){
        return patientFolder;
    }

    /* public static void NursePage(Scanner scan, String username){
        Nurse nurse = NurseDatabase.getNurse(username);

        System.out.println(getNurseName);
        System.out.println(getAge);
        System.out.println(getSex);
        System.out.println(getPosition);
        System.out.println(getShiftSchedule);
        System.out.println(getAreaAssignment);
        System.out.println(getPatientFolder);
        
    } 
    
    
    
    */

}
