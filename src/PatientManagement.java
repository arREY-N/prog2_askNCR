import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PatientManagement {

    private static Path nurseFolder = Paths.get("database\\nurse");
    private static Path patientFolder;
    private static Map<String, Patient> patientList = new TreeMap<>();

    public static void patientInformationManagement(Scanner scan, Nurse nurse) {
        patientList = PatientDatabase.getPatientList(nurse);
        patientFolder = nurseFolder.resolve(nurse.getUserID());

        boolean run = true;
        while (run) {
            System.out.println("Patient Information Management");
            System.out.println("A. View Patients");
            System.out.println("B. Add Patient");
            System.out.println("C. Remove Patient");
            System.out.println("D. Back");
            System.out.print("Enter your choice: ");
            String choice = scan.nextLine().toUpperCase().trim();
            System.out.println();

            switch (choice) {
                case "A":
                    getPatient(scan, nurse);
                    break;
                case "B":
                    addPatient(scan, nurse);
                    // PatientDatabase.loadToFile();
                    break;
                case "C":
                    removePatient(scan, nurse);
                    // PatientDatabase.loadToFile();
                    break;
                case "D":
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
            System.out.println("B. View Care Plan");
            System.out.println("C. Create Care Plan");
            System.out.println("D. Back");
            System.out.print("Enter your choice: ");
            String choice = scan.nextLine().toUpperCase().trim();
            System.out.println();

            switch (choice) {
                case "A":
                    showPatientInformation(patient);
                    System.out.print("Enter to continue...");
                    scan.nextLine();
                    break;
                case "B":
                    // carePlan.viewCarePlan(scan);
                    break;
                case "C":
                    carePlan.createCareRecommendation(scan, patient, nurse);
                    break;
                case "D":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
            
        }
    }

    public static void getPatient(Scanner scan, Nurse nurse) {
        showPatientList(nurse);
        System.out.println();
        System.out.print("Patient ID (0 to exit):");
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

    public static void showPatientInformation(Patient patient){
        System.out.println("\nPATIENT INFORMATION");
        System.out.println("Patient ID: " + patient.getPatientId());
        System.out.println("Patient Name: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Sex: " + patient.getSex());
        System.out.println("Diagnosis: " + patient.getDiagnosis());
        System.out.println();
    }

    public static void addPatient(Scanner scan, Nurse nurse) {
        System.out.print("Enter patient ID: ");
        String patientID = scan.nextLine().trim().toUpperCase();

        if(!PatientDatabase.getPatientID().containsKey(patientID)){
            System.out.print("Enter patient name: ");
            String name = scan.nextLine().toUpperCase();
            System.out.print("Enter patient age: ");
            int age = Integer.parseInt(scan.nextLine().trim().toUpperCase());
            System.out.print("Enter patient sex: ");
            String sex = scan.nextLine().trim().toUpperCase();
            System.out.print("Enter patient diagnosis: ");
            String diagnosis = scan.nextLine().trim().toUpperCase();
        
            patientList.put(patientID, new Patient(patientID, name, age, sex, diagnosis));
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
