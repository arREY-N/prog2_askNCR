import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PatientManagement {

    private static Path nurseFolder = Paths.get("database\\nurse");
    private static Path patientFolder;
    private static Map<String, Patient> patientList = new TreeMap<String, Patient>();

    public static void patientInformationManagement(Scanner scan, Nurse nurse) {
        PatientDatabase.loadNursePatients(nurse);
        patientList = PatientDatabase.getPatientList();
        patientFolder = nurseFolder.resolve(nurse.getUsername());

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
                    PatientDatabase.loadToFile(nurse);
                    break;
                case "C":
                    removePatient(scan);
                    PatientDatabase.loadToFile(nurse);
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
        showPatientList();
        System.out.println();
        System.out.print("Patient ID (0 to exit):");
        String input;

        if (!(input = scan.nextLine()).equals("0")){
            if(patientList.containsKey(input.trim())){
                managePatientInformation(scan, patientList.get(input));
            } else if(input.isEmpty()){
                System.out.println("\nInvalid input!\n");
                getPatient(scan, nurse);
            } else {
                System.out.println("\nPatient not found!\n");
                getPatient(scan, nurse);
            }
        }
    }

    public static void showPatientList(){
        patientList = PatientDatabase.getPatientList();
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

    public static void managePatientInformation(Scanner scan, Patient patient){
        boolean run = true;
        while (run) {
            System.out.println("\nPatient Information Management");
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
                    break;
                case "B":
                    System.out.println("Create care plan");
                    scan.nextLine();
                    // carePlan.viewCarePlan();
                    break;
                case "C":
                    System.out.println("View care plan");
                    scan.nextLine();
                    // carePlan.createCarePlan();
                    break;
                case "D":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
    }

    public static void addPatient(Scanner scan, Nurse nurse) {
        System.out.print("Enter patient ID: ");
        String patientID = scan.nextLine().trim().toUpperCase();

        if(patientList.containsKey(patientID)){
            System.out.println("\nPatient ID already in the system!\n");
        } else {
            System.out.print("Enter patient name: ");
            String name = scan.nextLine().toUpperCase();
            System.out.print("Enter patient age: ");
            int age = Integer.parseInt(scan.nextLine().trim().toUpperCase());
            System.out.print("Enter patient sex: ");
            String sex = scan.nextLine().trim().toUpperCase();
            System.out.print("Enter patient diagnosis: ");
            String diagnosis = scan.nextLine().trim().toUpperCase();

            patientList.put(patientID, new Patient(patientID, name, age, sex, diagnosis));
            try {
                fileManagement.createFolder(patientFolder, patientID);
            } catch (FolderCreationException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("\nPatient added successfully!\n");
        }
    }

    public static void removePatient(Scanner scan) {
        System.out.print("Enter patient ID to remove: ");
        String patientId = scan.nextLine().trim();

        if(patientList.containsKey(patientId)){
            patientList.remove(patientId);
            // fileManagement.removeFolder(nurseFolder.resolve(patientId), patientId);
            System.out.println("\nPatient removed successfully.\n");
        } else {
            System.out.println("\nPatient ID not in the system!\n");
            return;
        }        
    }
}
