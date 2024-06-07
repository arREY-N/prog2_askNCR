import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
public class carePlan {

    private static Path symptomFolderPath = Paths.get("database\\symptom\\symptomList");

    public static void createCareRecommendation(Scanner scan, Patient patient, Nurse nurse){        
        ArrayList<LinkedHashMap<String, ArrayList<String>>> carePlans = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
        LinkedHashMap<String, ArrayList<String>> fileContent = new LinkedHashMap<String, ArrayList<String>>();
        Map<String, Symptom> symptomList = SymptomDatabase.getSymptomList();
        ArrayList<String> symptomArray = patient.getSymptomFileList();    
        ArrayList<Symptom> possibleSymptomMatch = new ArrayList<Symptom>();     

        String patientDiagnosisName = patient.getDiagnosisName();
        String symptomName = null;
        Boolean read = true;    

        do{
            Symptom matchSymptom = null;
            System.out.println("Diagnosis: " + patientDiagnosisName);
            System.out.println("Symptoms: ");

            if(symptomArray.size()<1){
                System.out.println("\nNo symptoms to use\n");
                break;
            } else{
                for(int i=0; i<symptomArray.size(); i++){
                    System.out.println((i+1) + ". " + symptomArray.get(i));
                }
            
                System.out.print("Read symptoms to add (0 to complete): ");
                try{
                    int symptomNumber = Integer.parseInt(scan.nextLine());
                    System.out.println();
                    if(symptomNumber != 0){
                        try{
                            symptomName = symptomArray.get(symptomNumber-1);
    
                            for(Map.Entry<String, Symptom> symptom: symptomList.entrySet()){
                                String currentSymptomName = symptom.getKey();
                                    
                                int match = Utilities.matchTitles(currentSymptomName, symptomName);
    
                                switch (match) {
                                    case 1:
                                        matchSymptom = symptom.getValue();
                                        break;
                                    case 2:
                                        possibleSymptomMatch.add(symptom.getValue());
                                        break;
                                    default:
                                        break;
                                }
                            }
    
                            if(matchSymptom == null){
                                if(!possibleSymptomMatch.isEmpty()){
                                    boolean possibleMatch = true;
                                    System.out.println("Exact matches cannot be found!\n");
                                    do{
                                        System.out.println("Possible matches\n");
                    
                                        int i = 1;
                                            
                                        for(Symptom possibleSymptom: possibleSymptomMatch){
                                            System.out.println((i) + ". " + possibleSymptom.getSymptomName());
                                            i++;
                                        }
        
                                        System.out.print("Choose (0 to exit): ");
                                        try{
                                            int chosenMatch = Integer.parseInt(scan.nextLine());
                                            System.out.println();
                                            
                                            if(chosenMatch != 0){
                                                matchSymptom = possibleSymptomMatch.get(chosenMatch-1);
                                                symptomName = matchSymptom.getSymptomName();
                                            } 
                                            
                                            possibleMatch = false;
                                        } catch (NumberFormatException e){
                                            System.out.println("\nInvalid input!\n");
                                        }
                                    } while (possibleMatch);
                                }
                            }
                        } catch (NullPointerException e){
                            e.printStackTrace();
                        }
                            
                        try{
                            Path symptomPath = symptomFolderPath.resolve(matchSymptom.getFileName());
                         
                            fileContent = SymptomDatabase.getSymptomContents(symptomPath);
                         
                            Search.showSymptomInformation(scan, fileContent, symptomName);
                            carePlans.add(fileContent);
                         
                            System.out.printf("\n[%s] added to care plan! Enter to continue!\n", symptomArray.get(symptomNumber-1));
                            scan.nextLine();                            
                        } catch (NullPointerException e){
                            System.out.printf("\nSorry! [%s] is not yet available in the database!\n", symptomName.toUpperCase());
                            System.out.println("Add to database? (Y to add | Enter to go back)");
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
                        read = false; 
                    }
                } catch (NumberFormatException e){
                    System.out.println("\nInvalid Input!\n");
                }
            }
        } while (read);
        
        if(!carePlans.isEmpty()){
            fileManagement.createCareFile(carePlans, patient, nurse);
        } else {
            System.out.println("No contents to include!\n");
        }
    }
    
    public static File[] getCarePlans(Patient patient, Nurse nurse){
        Path filePath = Paths.get("database\\nurse\\" + nurse.getUserID() + "\\" + patient.getPatientId());
        File carePlans = new File(filePath.toString());
        File[] plans = carePlans.listFiles();
        
        return plans;
    }

    public static void viewCarePlan(Scanner scan, Patient patient, Nurse nurse){
        File[] plans = getCarePlans(patient, nurse);
        boolean careLoop = true;
        int response;
        do{
            int careNumber = 1;
            System.out.println("Care Recommendations\n");
            for(File plan: plans){
                System.out.println(careNumber + ". " + plan.getName());
                careNumber++;
            }

            System.out.print("Choose (0 to exit): ");

            try{
                response = Integer.parseInt(scan.nextLine());

                if(response != 0){
                    readCarePlan(scan, plans[response-1]);
                } else {
                    careLoop = false;
                }
            } catch (NumberFormatException e){
                System.out.println("\nInvalid Input!");
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("\nInvalid Input!");
            }

            System.out.println();
        } while (careLoop);
    }
    
    public static void readCarePlan(Scanner scan, File plan){
        try (BufferedReader reader = new BufferedReader(new FileReader(plan))) {
            String line;

            while((line=reader.readLine()) != null){
                System.out.println(line);
                scan.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}