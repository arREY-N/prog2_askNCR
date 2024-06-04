import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
public class carePlan {

    private static ArrayList<LinkedHashMap<String, ArrayList<String>>> careRecommendations = new ArrayList<>();

    public static void createCareRecommendation(Scanner scan, Patient patient, Nurse nurse){
        Boolean createLoop = true;

        do{
            System.out.println("\nCreate care recommendation!\n");
            System.out.println("A: Diagnosis-based Care Recommendations");
            System.out.println("B: Symptom-based Care Recommendations");
            System.out.println("C: Go Back");

            String response = scan.nextLine().toUpperCase().trim();
            switch (response) {
                case "A":
                    createDiagnosisCareRecommendation(scan, patient.getDiagnosis(), patient, nurse);
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

    public static void createDiagnosisCareRecommendation(Scanner scan, String patientDiagnosis, Patient patient, Nurse nurse){
        Map<String, Integer> diagnosisTable = DiagnosesDatabase.getDiagnosisTable();
        Map<String, Integer> possibleMatch = new TreeMap<String, Integer>();
        LinkedHashMap<String, ArrayList<String>> fileContents;
        Boolean diagnosisSymptomLoop = true;
        String diagnosisName;
        int diagnosisNumber;
        int symptomNumber;
        Boolean found = false;

        for(Map.Entry<String, Integer> diagnosis: diagnosisTable.entrySet()){
            if(found == false && fileMatches(diagnosis.getKey(), patientDiagnosis) != null && fileMatches(diagnosis.getKey(), patientDiagnosis).equals("match")){
                diagnosisName = DiagnosesDatabase.getDiagnosisList().get(diagnosisTable.get(diagnosis.getKey())).getDiagnosisName();
                diagnosisNumber = diagnosisTable.get(diagnosis.getKey());
                found = true;        
                do{
                    System.out.println();
                    System.out.println("Diagnosis: " + diagnosisName);
                        ArrayList<String> diagnosisSymptomList = Search.showDiagnosisSymptomList(diagnosisNumber);
                        try{
                            System.out.print("Choose Symptom (0 to exit): ");
                            if((symptomNumber = Integer.parseInt(scan.nextLine())) != 0){
                                try{
                                    fileContents = SymptomDatabase.getSymptom(diagnosisSymptomList.get(symptomNumber-1));
                                    careRecommendations.add(fileContents);
                                    Search.showSymptomInformation(scan, fileContents, diagnosisSymptomList.get(symptomNumber-1));
                                } catch (NullPointerException npe){
                                    System.out.println("Sorry! Symptom is not yet available in the database!");
                                    System.out.println("Add to database? (Y to add | Enter to go back)");
                                    String response = scan.nextLine().toUpperCase().trim();

                                    switch(response){
                                        case "Y":
                                            SymptomDatabase.createNewSymptom(scan);
                                            break;
                                        default:
                                            break;
                                    }
                                } catch (IndexOutOfBoundsException e){
                                    System.out.println("\nInvalid input!");
                                }
                            } else {
                                diagnosisSymptomLoop = false;
                            }
                        } catch (NumberFormatException nfe){
                            System.out.println("Invalid input!");
                        } 
                    }while(diagnosisSymptomLoop);

            } else if (found == false && fileMatches(diagnosis.getKey(), patientDiagnosis) != null && fileMatches(diagnosis.getKey(), patientDiagnosis).equals("part")){
                possibleMatch.put(diagnosis.getKey(), diagnosisTable.get(diagnosis.getKey()));
            } else{
                continue;
            }   

            if(found == true){
                System.out.println("Diagnosis found!");
                fileManagement.createCareFile(careRecommendations, patient, nurse);
                break;
            }
        }

        if(found == false){
            System.out.println("\nDiagnosis not found in the system!\n");
        }
    }

    public static String fileMatches(String fileName, String diagnosis){
        String match;
        String[] fileNameParts = fileName.toUpperCase().replaceAll("[^a-zA-Z ]", "").split(" ");
        String[] diagnosisParts = diagnosis.toUpperCase().replaceAll("[^a-zA-Z ]" , "").split(" ");

        Set<String> fileSet = new HashSet<>();
        Set<String> diagnosisSet = new HashSet<>();


        for(String parts1: fileNameParts){
            fileSet.add(parts1.trim());
        } 

        for(String parts2: diagnosisParts){
            diagnosisSet.add(parts2.trim());
        }

        if(!fileSet.equals(diagnosisSet)){
            diagnosisSet.retainAll(fileSet);

            if(!diagnosisSet.isEmpty()){
                match = "part";
            } else {
                match = null;
            }

        } else {
            match = "match";
        }

        return match;
    }
}
