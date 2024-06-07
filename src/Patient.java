import java.util.ArrayList;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String sex;
    private String diagnosisName;
    private Diagnosis diagnosis;
    private ArrayList<String> symptomFileList = new ArrayList<String>();
    private ArrayList<Symptom> symptomList = new ArrayList<Symptom>();

    public Patient(String patientId, String name, int age, String sex, String diagnosisName, ArrayList<String> symptomFileList) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.diagnosisName = diagnosisName;
        this.symptomFileList = symptomFileList;
    }

    public ArrayList<String> getSymptomFileList(){
        return symptomFileList;
    }

    public ArrayList<Symptom> getSymptomList() {
        for(String filename: symptomFileList){
            if(!filename.equals(" ")){
                symptomList.add(SymptomDatabase.getSymptom(filename));
            }
        }
        return symptomList;
    }

    public void setDiagnosis(Diagnosis diagnosis){
        this.diagnosis = diagnosis;
    }

    public void setDiagnosisName(String diagnosisName){
        this.diagnosisName = diagnosisName;
    }

    public String getDiagnosisName(){
        return diagnosisName;
    }

    public Diagnosis getDiagnosis(){
        return diagnosis;
    }

    public void setSymptomFileList(ArrayList<String> symptomFileList){
        ArrayList<String> newSymptomFileList = new ArrayList<String>();

        for(String file: symptomFileList){
            
            if(!file.trim().isEmpty()){
                newSymptomFileList.add(file);
            } else{
                continue;
            }
        }

        this.symptomFileList.clear();
        this.symptomFileList.addAll(newSymptomFileList);
    }

    public void addSymptomFileList(String filename){
        symptomFileList.add(filename);
    }

    public void removeSymptomFileList(String filename){
        symptomFileList.remove(filename);
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}