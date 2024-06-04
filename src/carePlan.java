import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class carePlan {

    public static void createCareRecommendation(Scanner scan, Patient patient){
        Boolean createLoop = true;

        do{
            System.out.println("\nCreate care recommendation!\n");
            System.out.println("A: Diagnosis-based Care Recommendations");
            System.out.println("B: Symptom-based Care Recommendations");
            System.out.println("C: Go Back");

            String response = scan.nextLine().toUpperCase().trim();
            switch (response) {
                case "A":
                    createDiagnosisCareRecommendation(scan, patient.getDiagnosis());
                    break;
                case "B":
                    System.out.println("Symptom-based care");
                    break;
                default:
                    createLoop = false;
                    break;
            }
        } while (createLoop);
    }

    public static void createDiagnosisCareRecommendation(Scanner scan, String patientDiagnosis){
        Map<String, Integer> diagnosisTable = DiagnosesDatabase.getDiagnosisTable();

        for(Map.Entry<String, Integer> diagnosis: diagnosisTable.entrySet()){
            if(fileMatches(diagnosis.getKey(), patientDiagnosis) == true){
                scan.nextLine();
            }
            

            // String diagnosisFileTitle =  diagnosis.getKey().replaceAll("[^a-zA-Z ]", " ");
            // ArrayList<String> diagnosisFileTitleParts = new ArrayList<>(Arrays.asList(diagnosisFileTitle.toUpperCase().split(" ")));

            // System.out.println("Diagnosis: " + patientDiagnosis);
            // for(String parts: diagnosisFileTitleParts){
            //     System.out.println(parts);
            // }

            // if(diagnosisFileTitleParts.contains(patientDiagnosis)){
            //     scan.nextLine();    
            //     int diagnosisNumber = diagnosis.getValue();
            //     int symptomNumber;
            //     Boolean diagnosisSymptomLoop = true;
                
            //     do{
            //         System.out.println();
            //         System.out.println("Diagnosis: " + DiagnosesDatabase.getDiagnosisList().get(diagnosisNumber).getDiagnosisName());
            //             ArrayList<String> diagnosisSymptomList = Search.showDiagnosisSymptomList(diagnosisNumber);
            //             try{
            //                 System.out.print("Choose Symptom (0 to exit): ");
            //                 if((symptomNumber = Integer.parseInt(scan.nextLine())) != 0){
            //                     try{
            //                         LinkedHashMap<String, ArrayList<String>> fileContents = SymptomDatabase.getSymptom(diagnosisSymptomList.get(symptomNumber-1));
            //                         Search.showSymptomInformation(scan, fileContents, diagnosisSymptomList.get(symptomNumber-1));
            //                     } catch (NullPointerException npe){
            //                         System.out.println("Sorry! Symptom is not yet available in the database!");
            //                         System.out.println("Add to database? (Y to add | Enter to go back)");
            //                         String response = scan.nextLine().toUpperCase().trim();

            //                         switch(response){
            //                             case "Y":
            //                                 SymptomDatabase.createNewSymptom(scan);
            //                                 break;
            //                             default:
            //                                 break;
            //                         }
            //                     }
            //                 } else {
            //                     diagnosisSymptomLoop = false;
            //                 }
            //             } catch (NumberFormatException nfe){
            //                 System.out.println("Invalid input!");
            //             } 
            //         }while(diagnosisSymptomLoop);
            // }
        }
     
    }

    public static Boolean fileMatches(String fileName, String diagnosis){
        Boolean found = null;
        String[] fileNameParts = fileName.toUpperCase().replaceAll("[^a-zA-Z ]", "").split(" ");
        String[] diagnosisParts = diagnosis.toUpperCase().replaceAll("[^a-zA-Z ]" , "").split(" ");

        Set<String> fileSet = new HashSet<>();
        Set<String> diagnosisSet = new HashSet<>();

        System.out.println("Filename Parts:");
        for(String parts1: fileNameParts){
            fileSet.add(parts1.trim());
            System.out.print(parts1.trim() + " ");
        } 
        System.out.println();

        System.out.println("Diagnosis Name Parts:");
        for(String parts2: diagnosisParts){
            diagnosisSet.add(parts2.trim());
            System.out.print(parts2.trim() + " ");
        }
        System.out.println();

        if(!fileSet.equals(diagnosisSet)){
            diagnosisSet.retainAll(fileSet);

            if(!diagnosisSet.isEmpty()){
                System.out.println("parts of the filename matches");
                found = true;
            } else {
                found = false;
            }

        } else {
            System.out.println("filename matches");
            found = true;
        }

        return found;
    }
}
