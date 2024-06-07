import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Search {

    private static Path symptomsPath = Paths.get("database\\symptom\\symptomList");
    
    public static void chooseDiagnosis(Scanner scan){
        Boolean diagnosisLoop = true;
        int diagnosisNumber;

        do{
            System.out.println("DIAGNOSES LIST");
            
            Map<Integer, Diagnosis> diagnosisList = DiagnosesDatabase.getDiagnosisList(); 

            showDiagnosesList(diagnosisList);            

            try{
                System.out.print("Choose Diagnosis (0 to exit): ");
                if((diagnosisNumber = Integer.parseInt(scan.nextLine())) != 0){
                    Diagnosis diagnosis = diagnosisList.get(diagnosisNumber);
                    chooseSymptom(scan, diagnosis);
                } else {
                    System.out.println();
                    diagnosisLoop = false;
                }
            } catch (NumberFormatException nfe){
                System.out.println("Invalid input!");
            } 
        }while(diagnosisLoop);
    }

    public static ArrayList<LinkedHashMap<String, ArrayList<String>>> chooseSymptom(Scanner scan, Diagnosis diagnosis){
        ArrayList<String> diagnosisSymptomList = getDiagnosisSysmptomList(diagnosis);   // provides the diagnosis symptoms
        Map<String, Symptom> symptomList = SymptomDatabase.getSymptomList();            // provides a map of all symptoms
        
        LinkedHashMap<String, ArrayList<String>> fileContents = null;                   // contents of a symptom
        ArrayList<LinkedHashMap<String, ArrayList<String>>> carePlans = new ArrayList<>();  // array of selected care recos

        boolean diagnosisSymptomLoop = true;
        int symptomNumber;
        String symptomName = null;
        String diagnosisName = diagnosis.getDiagnosisName();

        do{ 
            String matchSymptom = null;
            ArrayList<Symptom> possibleMatchSymptoms = new ArrayList<>();
            System.out.println("\nDiagnosis: " + diagnosisName);
                       
            showDiagnosisSymptomList(diagnosisSymptomList);
            
            try{
                System.out.print("Choose Symptom (0 to exit): ");
                if((symptomNumber = Integer.parseInt(scan.nextLine())) != 0){
                    try{
                        symptomName = diagnosisSymptomList.get(symptomNumber-1);

                        for(Map.Entry<String, Symptom> symptom: symptomList.entrySet()){
                            String fileTitle = symptom.getKey();
                            Symptom currentSymptom = symptom.getValue();

                            int matches = Utilities.matchTitles(fileTitle, symptomName);
                                
                            switch (matches) {
                                case 1:
                                    matchSymptom = currentSymptom.getFileName();
                                    break;
                                case 2:
                                    possibleMatchSymptoms.add(currentSymptom);
                                    break;
                                default:
                                    break;
                            }
                        }

                        if(matchSymptom == null){
                            if(!possibleMatchSymptoms.isEmpty()){
                                boolean possible = true;
                                
                                System.out.println("\nExact matches cannot be found!\n");
                                do{
                                    System.out.println("Possible matches\n");
                                            
                                    for(int i = 0; i<possibleMatchSymptoms.size(); i++){
                                        Symptom possibleSymptom = possibleMatchSymptoms.get(i);
                                        System.out.println((i+1) + ". " + possibleSymptom.getSymptomName());
                                    }
                
                                    System.out.print("Choose (0 to exit): ");
                                    try{
                                        int chosenMatch = Integer.parseInt(scan.nextLine());
                    
                                        if(chosenMatch != 0){
                                            System.out.println();
                                            matchSymptom = possibleMatchSymptoms.get(chosenMatch-1).getFileName();
                                            symptomName = possibleMatchSymptoms.get(chosenMatch-1).getSymptomName();

                                            fileContents = SymptomDatabase.getSymptomContents(symptomsPath.resolve(matchSymptom));
                                            showSymptomInformation(scan, fileContents, symptomName);
                                            carePlans.add(fileContents);
                                            
                                            possible = false;
                                        } else {
                                            System.out.printf("\nSorry! [%s] is not yet available in the database!\n", symptomName.toUpperCase());
                                            System.out.print("Add to database? (Y to add | Enter to go back)");
                                            String response = scan.nextLine().toUpperCase().trim();
                                
                                            switch(response){
                                                case "Y":
                                                    SymptomDatabase.createNewSymptom(scan);
                                                    break;
                                                default:
                                                    break;
                                            }
                                            possible = false;
                                        }                                        
                                    } catch (NumberFormatException e){
                                        System.out.println("\nInvalid input\n");
                                    }
                                } while (possible);

                            } else {
                                System.out.printf("\nSorry! [%s] is not yet available in the database!\n", symptomName.toUpperCase());
                                System.out.print("Add to database? (Y to add | Enter to go back)");
                                String response = scan.nextLine().toUpperCase().trim();
                    
                                switch(response){
                                    case "Y":
                                        SymptomDatabase.createNewSymptom(scan);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            fileContents = SymptomDatabase.getSymptomContents(symptomsPath.resolve(matchSymptom));
                            showSymptomInformation(scan, fileContents, symptomName);
                            carePlans.add(fileContents);
                        }
                    } catch (IndexOutOfBoundsException e){
                        System.out.println("\nInvalid input!");
                    }
                } else {
                    diagnosisSymptomLoop = false;
                }
            } catch (NumberFormatException e){
                System.out.println("\nInvalid Input!");
            }
        }while(diagnosisSymptomLoop);

        return carePlans;
    }

    public static void showDiagnosesList(Map<Integer, Diagnosis> diagnosisList){
        for(Map.Entry<Integer, Diagnosis> diagnosis: diagnosisList.entrySet()){
            System.out.println(diagnosis.getKey() + ". " + diagnosis.getValue().getDiagnosisName());
        }
    }

    public static void showDiagnosisSymptomList(ArrayList<String> diagnosisSymptomList){
        int symptomNumber = 1;

        for(String symptom: diagnosisSymptomList){
            System.out.println(symptomNumber + ". " + symptom);
            symptomNumber++;
        }
    }

    public static ArrayList<String> getDiagnosisSysmptomList(Diagnosis diagnosis){
        ArrayList<String> diagnosisSymptomList = diagnosis.getSymptomList();
        return diagnosisSymptomList;
    }

    public static void chooseSymptomList(Scanner scan){
        Boolean symptomLoop = true;
        int symptomNumber;
        String symptomName = null;

        do{
            System.out.println("SYMPTOM LIST");
            ArrayList<String> symptomList = showSymptomList();
            try{
                System.out.print("Choose Symptom (0 to exit): ");
                if((symptomNumber = Integer.parseInt(scan.nextLine())) != 0){
                    System.out.println();
                    try{
                        Symptom currentSymptom = SymptomDatabase.getSymptomList().get(symptomList.get(symptomNumber-1));
                        
                        String symptomFilename = currentSymptom.getFileName();
                        symptomName = currentSymptom.getSymptomName();
                        Path symptomFilepath = symptomsPath.resolve(symptomFilename);

                        LinkedHashMap<String, ArrayList<String>> fileContents =  SymptomDatabase.getSymptomContents(symptomFilepath);
                        
                        showSymptomInformation(scan, fileContents, symptomName);
                    } catch (NullPointerException npe){
                        System.out.printf("\nSorry! [%s] is not yet available in the database!\n", symptomName.toUpperCase());
                    } catch (IndexOutOfBoundsException e){
                        System.out.println("\nInvalid input!\n");
                    }
                } else {
                    System.out.println();
                    symptomLoop = false;
                }
            } catch (NumberFormatException nfe){
                System.out.println("\nInvalid input!\n");
            } 
        }while(symptomLoop); 
    }

    public static void showSymptomInformation(Scanner scan, LinkedHashMap<String, ArrayList<String>> fileContents, String symptomName){
        for(Map.Entry<String, ArrayList<String>> content : fileContents.entrySet()){
            if(content.getKey().equalsIgnoreCase(symptomName)){
                System.out.println("-----" + content.getKey() + "-----\n");
            } else {
                System.out.println("=====" + content.getKey());
                for(String texts: content.getValue()){
                    if(texts != ""){
                        System.out.println(texts);
                        scan.nextLine();
                    }
                }
            }
        }
        System.out.print("End of document. Enter to continue...");
        scan.nextLine();
    }

    public static ArrayList<String> showSymptomList(){
        ArrayList<String> symptomArray = SymptomDatabase.getSymptomArray();

        for(int i = 1; i<=symptomArray.size(); i++){
            System.out.println(i + ". " + symptomArray.get(i-1));
        }

        return symptomArray;
    }
}
