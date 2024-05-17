public class Diagnosis {
    public String name;
    public String fileName;

    Diagnosis(String name, String fileName){
        this.name = name;
        this.fileName = fileName;
    }

    public String getDiagnosisName(){
        return name;
    }

    public String getFileName(){
        return fileName;
    }
}
