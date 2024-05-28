import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        AccountsDatabase.loadFromFile();    // loads txt files into the program
        NurseDatabase.loadFromFile();
        PatientDatabase.loadFromFile();
        DiagnosesDatabase.loadFromFile();
        Scanner scan = new Scanner(System.in);
        Boolean run = true;
        
        while(run == true){
            System.out.println("What are you here for?");
            System.out.println("A. Login");
            System.out.println("B. Sign Up");
            System.out.println("C. Diagnoses Database");
            System.out.println("D. Exit");
            String input = scan.nextLine().trim().toUpperCase();

            if(!input.isEmpty()){
                char value = input.charAt(0);
                switch (value) {
                    case 'A':
                        loginMenu(scan);
                        break;
                    case 'B':
                        signupMenu(scan);
                        break;
                    case 'C':
                        chooseDiagnosis(scan);
                        break;    
                    case 'D':
                        run = false;
                        AccountsDatabase.loadToFile();
                        System.out.println("See you soon, our nurse!\n");
                        break;
                    default:
                        System.out.println("Invalid input!\n");
                        break;
                }
            } else {
                System.out.println("Invalid input!\n");
            }
        }   
        scan.close();
    }

    public static void loginMenu(Scanner scan){
        System.out.println("\nWelcome back!");
        System.out.print("Username: ");
        String username = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();

        // first checks if the input is a valid input (alphanumeric only)
        if(Validation.isAlphanumeric(username, password) == true){
            // if true, calls the login() method in the Validation class
            Validation.login(username, password);
            homePage(scan, username);
        } else {
            System.out.println("Input must be alphanumeric only");
        }
    }
    
    public static void signupMenu(Scanner scan){
        System.out.println("\nWelcome!");
        System.out.print("Username: ");
        String username = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();

        // first checks if the input is a valid input (alphanumeric only)
        if(Validation.isAlphanumeric(username, password) == true){
            // if true, calls the login() method in the Validation class
          Validation.signup(username, password);
        } else {
            System.out.println("Input must be alphanumeric only");
        }
    }

    public static void chooseDiagnosis(Scanner scan){
        DiagnosesDatabase.showDiagnoses();
        System.out.print("Read: ");
        DiagnosesDatabase.getDiagnosis(Integer.parseInt(scan.nextLine()));        
    }

    public static void homePage(Scanner scan, String username){
        Nurse nurse = NurseDatabase.getNurseAccounts().get(username);
        if (nurse == null) {
            System.out.println("Nurse not found!");
            return;
        }

        boolean run = true;
        while (run) {
            System.out.println("What do you like to see?");
            System.out.println("A. Nurse's Information");
            System.out.println("B. Patient's Information Management");
            System.out.println("C. Symptoms' Page");
            System.out.println("D. Log out");
            String choice = scan.nextLine().trim().toUpperCase();
            char value = choice.charAt(0); 
            switch (value) {
            
            case 'A':
                nursePage(scan, username);
                break;
            case 'B':
                patientInformationManagement(scan, username);
                break;
            case 'C':
                symptomsPage(scan);
                break;
            case 'D':
                run = false;
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                System.out.println();
                homePage(scan, username);
                break;
            } 
        }
    }

    private static void nursePage() {
        System.out.println("\nNurse Information");
        System.out.println("Name: " + Nurse.getNurseName());
        System.out.println("Age: " + Nurse.getAge());
        System.out.println("Sex: " + Nurse.getNurseName());
        System.out.println("Position: " + Nurse.getNurseName());
        System.out.println("Shift Schedule: " + Nurse.getNurseName());
        System.out.println("Area Assignment: " + Nurse.getAreaAssignment());
        System.out.println();
    }

    private static void patientInformationManagement(Scanner scan, Nurse nurse) {
        boolean run = true;
        while (run) {
            System.out.println("\nPatient Information Management");
            System.out.println("1. View Patient List");
            System.out.println("2. Add Patient");
            System.out.println("3. Remove Patient");
            System.out.println("4. Back");
            System.out.println("Enter your choice: ");
            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    viewPatientList(scan, nurse);
                    break;
                case "2":
                    addPatient(scan, nurse);
                    break;
                case "3":
                    removePatient(scan, nurse);
                    break;
                case "4":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void viewPatientList(Scanner scan, Nurse nurse) {
        PatientDatabase.showPatientsDetails(nurse);

        System.out.println("Enter patient name to view details or 'back' to return: ");
        String input = scan.nextLine().trim();
        if (!input.equalsIgnoreCase("Back")) {
            PatientDatabase.showPatientsDetails(input);
        }
    }

    private static void addPatient(Scanner scan, Nurse nurse) {
        System.out.print("Enter patient ID: ");
        String patientId = scan.nextLine().trim();
        System.out.print("Enter patient name: ");
        String name = scan.nextLine();
        System.out.print("Enter patient age: ");
        int age = Integer.parseInt(scan.nextLine().trim());
        System.out.print("Enter patient sex: ");
        String sex = scan.nextLine().trim();
        System.out.print("Enter patient diagnosis: ");
        String diagnosis = scan.nextLine().trim();
        System.out.print("Enter care recommendation: ");
        String careRecommendatio = scan.nextLine().trim();

        Patient newPatient = new Patient(patientId, name, age, sex, diagnosis, careRecommendatio);
        PatientDatabase.addPatientAccount(patientId, newPatient);
        System.out.println("Patient added successfully.");
    }

    private static void removePatient(Scanner scan, Nurse nurse) {
        System.out.println("Enter patient ID to remove: ");
        String patientId = scan.nextLine().trim();
        PatientDatabase.removePatient(patientId);
        System.out.println("Patient removed successfully.");
    }

    private static void symptomsPage(Scanner scan) {
        System.out.println("Symptoms' Page - Not Implemented Yet");
    }
}
