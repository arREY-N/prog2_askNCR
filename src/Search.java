import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Search {
    
    public static void chooseDiagnosis(Scanner scan){
        Boolean diagnosisLoop = true;
        Boolean diagnosisSymptomLoop = true;
        int diagnosisNumber;
        int symptomNumber;

        do{
            System.out.println("DIAGNOSES LIST");
            Map<Integer, Diagnosis> diagnosisList = showDiagnosesList();
            try{
                System.out.print("Choose Diagnosis (0 to exit): ");
                if((diagnosisNumber = Integer.parseInt(scan.nextLine())) != 0){
                    do{
                        System.out.println();
                        System.out.println("Diagnosis: " + diagnosisList.get(diagnosisNumber).getDiagnosisName());
                        ArrayList<String> diagnosisSymptomList = showDiagnosisSymptomList(diagnosisNumber);
                        try{
                            System.out.print("Choose Symptom (0 to exit): ");
                            if((symptomNumber = Integer.parseInt(scan.nextLine())) != 0){
                                SymptomDatabase.getSymptom(scan, diagnosisSymptomList.get(symptomNumber-1));
                            } else {
                                diagnosisSymptomLoop = false;
                            }
                        } catch (NumberFormatException nfe){
                            System.out.println("Invalid input!");
                        }
                    }while(diagnosisSymptomLoop);
                } else {
                    System.out.println();
                    diagnosisLoop = false;
                }
            } catch (NumberFormatException nfe){
                System.out.println("Invalid input!");
            }
        }while(diagnosisLoop); 
    }

    public static Map<Integer, Diagnosis> showDiagnosesList(){
        Map<Integer, Diagnosis> diagnosisList = DiagnosesDatabase.getDiagnosisList();
        
        for(Map.Entry<Integer, Diagnosis> diagnosis: DiagnosesDatabase.getDiagnosisList().entrySet()){
            System.out.println(diagnosis.getKey() + ". " + diagnosis.getValue().getDiagnosisName());
        }

        return diagnosisList;
    }

    public static ArrayList<String> showDiagnosisSymptomList(int diagnosisNumber){
        ArrayList<String> diagnosisSymptomList = DiagnosesDatabase.getDiagnosisList().get(diagnosisNumber).getSymptomList();
        int symptomNumber = 1;
        for(String symptom: diagnosisSymptomList){
            System.out.println(symptomNumber + ". " + symptom);
            symptomNumber++;
        }
        return diagnosisSymptomList;
    }

    public static void chooseSymptoms(Scanner scan){
        Boolean symptomLoop = true;
        int symptomNumber;

        do{
            System.out.println("SYMPTOM LIST");
            ArrayList<String> symptomList = showSymptomList();
            try{
                System.out.print("Choose Diagnosis (0 to exit): ");
                if((symptomNumber = Integer.parseInt(scan.nextLine())) != 0){
                    SymptomDatabase.getSymptom(scan, symptomList.get(symptomNumber-1));
                } else {
                    System.out.println();
                    symptomLoop = false;
                }
            } catch (NumberFormatException nfe){
                System.out.println("Invalid input!");
            }
        }while(symptomLoop); 
    }

    public static ArrayList<String> showSymptomList(){
        ArrayList<String> symptomList = SymptomDatabase.getSymptomList();

        for(int i = 1; i<=symptomList.size(); i++){
            System.out.println(i + ". " + symptomList.get(i-1));
        }

        return symptomList;
    }
}
