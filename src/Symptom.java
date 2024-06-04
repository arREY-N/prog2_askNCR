public class Symptom {
    public String name;
    public String fileName;
    
    Symptom(String name, String fileName){
        this.name = name;
        this.fileName = fileName;
    }
    
    public String getSymptomName(){
        return name;
    }

    public String getFileName(){
        return fileName;
    }
}
