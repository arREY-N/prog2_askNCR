import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class PatientManagement {

    private static Path nurseFolder = Paths.get("database\\nurse");
    private static Path patientFolder;
    private static Map<String, Patient> patientList = new TreeMap<String, Patient>();

    public static void patientInformationManagement(Scanner scan, Nurse nurse) {
        patientList = PatientDatabase.getPatientList(nurse);

        if(patientList == null) {
            scan.nextLine();
        }
        patientFolder = nurseFolder.resolve(nurse.getUserID());

        boolean run = true;
        while (run) {
            System.out.println("Patient Information Management");
            System.out.println("A. View Patients");
            System.out.println("B. Add Patient");
            System.out.println("C. Remove Patient");
            System.out.println("X. Back");
            System.out.print("Enter your choice: ");
            String choice = scan.nextLine().toUpperCase().trim();
            System.out.println();

            switch (choice) {
                case "A":
                    getPatient(scan, nurse);
                    break;
                case "B":
                    addPatient(scan, nurse);
                    PatientDatabase.loadToFile();
                    break;
                case "C":
                    removePatient(scan, nurse);
                    PatientDatabase.loadToFile();
                    break;
                case "X":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
    }

    public static void managePatientInformation(Scanner scan, Patient patient, Nurse nurse){
        boolean run = true;
        System.out.println();
        while (run) {
            System.out.println("Patient Information Management");
            System.out.println("A. View Patient Information");
            System.out.println("B. Change Patient Information");
            System.out.println("C. View Care Plan");
            System.out.println("D. Create Care Plan");
            System.out.println("E. Add Symptoms");
            System.out.println("F. Remove Symptoms");
            System.out.println("X. Back");
            System.out.print("Enter your choice: ");
            String choice = scan.nextLine().toUpperCase().trim();
            System.out.println();

            switch (choice) {
                case "A":
                    showPatientInformation(patient, scan);
                    PatientDatabase.loadToFile();
                    break;
                case "B":
                    changePatientInformation(scan, patient);    
                    break;
                case "C":
                    carePlan.viewCarePlan(scan, patient, nurse);    
                    break;
                case "D":
                    carePlan.createCareRecommendation(scan, patient, nurse);
                    break;
                case "E":
                    addSymptoms(scan, patient);
                    break;
                case "F":
                    removeSymptoms(scan, patient);
                    break;
                case "X":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
    }

    public static void showSymptoms(Patient patient){
        ArrayList<String> symptomFileList = patient.getSymptomFileList();
        
        if(!symptomFileList.isEmpty()){
            for(int i=0; i<symptomFileList.size(); i++){
                System.out.println((i+1) + ". " + symptomFileList.get(i));
            }
        } else{
            System.out.println("\n[List is currently empty]");
        }
    }

    public static void addSymptoms(Scanner scan, Patient patient) {
        System.out.println("Current symptoms");

        showSymptoms(patient);    

        scan.nextLine();
        System.out.println();

        Map<String, Symptom> symptomList = SymptomDatabase.getSymptomList();
        ArrayList<Symptom> symptomArray = new ArrayList<Symptom>();
        
        int i = 1;
        for(Map.Entry<String, Symptom> symptom: symptomList.entrySet()){
            symptomArray.add(symptom.getValue());
            System.out.println(i + ". " + symptom.getKey());
            i++;
        }

        Set<Integer> addSymptoms = new HashSet<>();

        boolean adding = true;
        do{
            System.out.print("Add symptom (0 to end): ");
            try{
                int symptomNumber = Integer.parseInt(scan.nextLine());

                if(symptomNumber == 0){
                    adding = false;
                } else {
                    if(!addSymptoms.contains(symptomNumber)){
                        addSymptoms.add(symptomNumber);
                        Symptom addSymptom = symptomArray.get(symptomNumber-1);
                        patient.addSymptomFileList(addSymptom.getSymptomName());
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("\nInvalid input!\n");
            } catch (IndexOutOfBoundsException e){
                System.out.println("\nInvalid input!\n");
            }
        } while (adding);
        System.out.println("\nUpdated symptom list\n");
        showSymptoms(patient);
        System.out.println("\nEnter to continue...");
        scan.nextLine();
    }

    public static void removeSymptoms(Scanner scan, Patient patient) {
        ArrayList<String> symptomFileList = patient.getSymptomFileList();
        ArrayList<String> removeList = new ArrayList<String>();

        System.out.println("Current symptoms");

        showSymptoms(patient);    

        boolean removing = true;
        do{
            System.out.print("Remove symptom (0 to end): ");
            try{
                int symptomNumber = Integer.parseInt(scan.nextLine());

                if(symptomNumber == 0){
                    removing = false;
                } else {
                    String removeSymptom = symptomFileList.get(symptomNumber-1);
                    removeList.add(removeSymptom);
                }
            } catch (NumberFormatException e){
                System.out.println("\nInvalid input!\n");
            } catch (IndexOutOfBoundsException e){
                System.out.println("\nInvalid input!\n");
            }
            
        } while (removing);

        if(!removeList.isEmpty()){
            for(String removeSympom: removeList){
                symptomFileList.remove(removeSympom);        
            }
        }

        patient.setSymptomFileList(symptomFileList);
        System.out.println("\nUpdated symptom list\n");
        showSymptoms(patient);
        System.out.println("\nEnter to continue...\n");
        scan.nextLine();
    }

    public static void getPatient(Scanner scan, Nurse nurse) {
        showPatientList(nurse);
        System.out.println();
        System.out.print("Patient ID (0 to exit): ");
        String input;

        if (!(input = scan.nextLine()).equals("0")){
            if(patientList.containsKey(input.trim())){
                managePatientInformation(scan, patientList.get(input), nurse);
            } else if(input.isEmpty()){
                System.out.println("\nInvalid input!\n");
                getPatient(scan, nurse);
            } else {
                System.out.println("\nPatient not found!\n");
                getPatient(scan, nurse);
            }
        } else {
            System.out.println();
        }
    }

    public static void showPatientList(Nurse nurse){
        patientList = PatientDatabase.getPatientList(nurse);

        System.out.println("PATIENT ID | PATIENT NAME");   
        System.out.println("--------------------------");     
        for(Map.Entry<String, Patient> patient: patientList.entrySet()){
            System.out.printf("%-10s | %s", patient.getValue().getPatientId(), patient.getValue().getName());
            System.out.println();
        }
    }

    public static void showPatientInformation(Patient patient, Scanner scan){
        String diagnosisName;
        System.out.println("PATIENT INFORMATION");
        System.out.println("Patient ID: " + patient.getPatientId());
        System.out.println("Patient Name: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Sex: " + patient.getSex());
        
        if(patient.getDiagnosisName().equals(" ")){
            System.out.println("Diagnosis not set!");
            
            System.out.print("Y to set | Enter if no: ");
            
            String action = scan.nextLine().trim().toUpperCase();

            if(!action.isBlank()){
                char option = action.charAt(0);

                switch (option) {
                    case 'Y':
                        System.out.println("Set Diagnosis");
                        Map<Integer, Diagnosis> diganosisList = DiagnosesDatabase.getDiagnosisList();
                        
                        for(Map.Entry<Integer, Diagnosis> diagnosis: diganosisList.entrySet()){
                            diagnosisName = diagnosis.getValue().getDiagnosisName();
                            System.out.println(diagnosis.getKey() + ". " + diagnosisName);
                        }
    
                        int response;
                        boolean choose = true;
                        try{
                            do{
                                System.out.print("Choose Diagnosis (0 if none): ");
                                response = Integer.parseInt(scan.nextLine());
                                if(response != 0){
                                    Diagnosis diagnosis = diganosisList.get(response);
                                    patient.setDiagnosisName(diagnosis.getDiagnosisName());
                                    patient.setSymptomFileList(diagnosis.getSymptomList());
                                }
                                choose = false;
                            } while (choose);
            
                        } catch (NumberFormatException e){
                            System.out.println("\nInvalid input!");
                        } catch (NullPointerException e){
                            System.out.println("\nInvalid input!");
                        }            
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println("\nDiagnosis: " + patient.getDiagnosisName());
        ArrayList<String> symptomFileList = patient.getSymptomFileList();
        
        if(!symptomFileList.isEmpty()){
            for(int i=0; i<symptomFileList.size(); i++){
                System.out.println((i+1) + ". " + symptomFileList.get(i));
            }
        } else{
            System.out.println("\nList is currently empty");
        }

        System.out.print("\nEnter to continue...");
        scan.nextLine();
        System.out.println();
    }

    public static void changePatientInformation(Scanner scan, Patient patient){
        System.out.println("Edit Patient Information");
        
        System.out.println("Old patient name: " + patient.getName());
        System.out.print("New Patient name: ");
        String name = scan.nextLine().toUpperCase();
        patient.setName(name);

        System.out.println("Old patient age: " + patient.getAge());
        boolean invalid = true;
        do{
            System.out.print("New patient age: ");
            try{
                int age = Integer.parseInt(scan.nextLine().trim().toUpperCase());
                patient.setAge(age);
                invalid = false;
            } catch (NumberFormatException e){
                System.out.println("\nInvalid input!\n");
            }
        } while (invalid);
        
        System.out.println("Old patient sex: "+ patient.getSex());
        System.out.print("New patient sex (M/F): ");
        String sex = scan.nextLine().trim().toUpperCase();        
        patient.setSex(sex);

        System.out.println("Old Diagnosis: " + patient.getDiagnosisName().toUpperCase());
        
        scan.nextLine();
        
        String diagnosisName;
        
        Map<Integer, Diagnosis> diganosisList = DiagnosesDatabase.getDiagnosisList();
                        
        for(Map.Entry<Integer, Diagnosis> diagnosis: diganosisList.entrySet()){
            diagnosisName = diagnosis.getValue().getDiagnosisName();
            System.out.println(diagnosis.getKey() + ". " + diagnosisName);
        }

        int response;
        boolean choose = true;

        try{
            do{
                System.out.print("New Diagnosis: ");
                response = Integer.parseInt(scan.nextLine());
                if(response != 0){
                    Diagnosis diagnosis = diganosisList.get(response);
                    patient.setDiagnosisName(diagnosis.getDiagnosisName());
                    patient.setSymptomFileList(diagnosis.getSymptomList());
                }
                choose = false;
            } while (choose);

        } catch (NumberFormatException e){
            System.out.println("\nInvalid input!");
        } catch (NullPointerException e){
            System.out.println("\nInvalid input!");
        }            

        System.out.println();
    }

    public static void addPatient(Scanner scan, Nurse nurse) {
        System.out.print("Enter patient ID: ");
        String patientID = scan.nextLine().trim().toUpperCase();

        if(!PatientDatabase.getPatientID().containsKey(patientID)){
            System.out.print("Enter patient name: ");
            String name = scan.nextLine().toUpperCase();
            
            boolean invalid = true;
            int age = 0;
            do{
                System.out.print("Enter patient age: ");
                try{
                    age = Integer.parseInt(scan.nextLine().trim().toUpperCase());
                    invalid = false;
                } catch (NumberFormatException e){
                    System.out.println("\nInvalid input!\n");
                }
            } while (invalid);

            System.out.print("Enter patient sex (M/F): ");
            String sex = scan.nextLine().trim().toUpperCase();
        
            patientList.put(patientID, new Patient(patientID, name, age, sex, " ", new ArrayList<String>()));

            PatientDatabase.getPatientID().put(patientID, nurse.getUserID());
            
            try {
                fileManagement.createFolder(patientFolder, patientID);
            } catch (FolderCreationException e) {
                System.out.println(e.getMessage());
            }
            
            System.out.println("\nPatient added successfully!\n");
            PatientDatabase.loadToFile();
        } else {
            System.out.println("\nPatient ID already existing!\n");
            String nurseID = PatientDatabase.getPatientID().get(patientID);
            String nurseName = NurseDatabase.getNurseInformation().get(nurseID).getNurseName();
            String patientName = PatientDatabase.getNursePatients().get(nurseID).get(patientID).getName();
            System.out.println("Patient Name: " + patientName);
            System.out.println("Nurse: " + nurseName);
            System.out.println();
        }
        System.out.print("Enter to continue...\n");
        scan.nextLine();        
    }

    public static void removePatient(Scanner scan, Nurse nurse) {
        showPatientList(nurse);
        System.out.println();
        System.out.print("Enter patient ID to remove: ");
        String patientId = scan.nextLine().trim();

        if(patientList.containsKey(patientId)){
            patientList.remove(patientId);
            fileManagement.removeFolder(nurseFolder.resolve(nurse.getUserID()).resolve(patientId));
            System.out.println("\nPatient removed successfully.\n");
            PatientDatabase.loadToFile();
        } else {
            System.out.println("\nPatient ID not found!\n");
        }        
    }
}
