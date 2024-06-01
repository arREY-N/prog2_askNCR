import java.util.ArrayList;

public class Diagnosis {
    public String name;
    public String fileName;
    public ArrayList<String> symptomList = new ArrayList<String>();

    Diagnosis(String name, String fileName, ArrayList<String> symptomList){
        this.name = name;
        this.fileName = fileName;
        this.symptomList = symptomList;
    }

    public void setDiagnosisName(String name){
        this.name = name;
    }

    public void addSymptom(String symptomName){
        this.symptomList.add(symptomName);
    }

    public String getDiagnosisName(){
        return name;
    }

    public String getFileName(){
        return fileName;
    }

    public ArrayList<String> getSymptomList(){
        return symptomList;
    }
}
