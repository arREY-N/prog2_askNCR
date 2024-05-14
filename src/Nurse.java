public class Nurse {
    private String username;
    private String nurseName;
    private String sex;
    private int age;
    private String position;
    private String shiftSchedule;
    private String areaAssignment;
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

    public String getPatientFolder(){
        return patientFolder;
    }
}
