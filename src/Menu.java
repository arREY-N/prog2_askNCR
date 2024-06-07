import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
public class Menu {
    private static Path nursePath = Paths.get("database\\nurse");
    public static void main(String[] args) {
        AccountsDatabase.loadFromFile();    // loads txt files into the program
        NurseDatabase.loadFromFile();
        DiagnosesDatabase.loadFromFile();
        NurseDatabase.loadFromFile();
        SymptomDatabase.loadFromFile();
        PatientDatabase.loadFromFile();
        
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        
        while(run == true){
            System.out.println("What are you here for?");
            System.out.println("A. Login");
            System.out.println("B. Sign Up");
            System.out.println("X. Exit");
            System.out.print("Enter your choice: ");
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
                    case 'X':    
                        run = false;
                        AccountsDatabase.loadToFile();
                        NurseDatabase.loadToFile();
                        PatientDatabase.loadToFile();
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
        
        if(username.equals(AccountsDatabase.getAdminName()) && password.equals(AccountsDatabase.getAdminPassword())){
            adminHome(scan); 
        } else {
            if(Validation.isAlphanumeric(username, password) == true){
                try{
                    Validation.login(username, password);
        
                    homepage(scan, username);
                } catch (InvalidCredentialsException e){
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            } else {
                System.out.println("Input must be alphanumeric only");
            }
        }
        System.out.println();
    }
    
    public static void signupMenu(Scanner scan){
        System.out.println("\nWelcome!");
        System.out.print("Username: ");
        String username = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.println();

        // first checks if the input is a valid input (alphanumeric only)
        if(Validation.isAlphanumeric(username, password) == true){
            // if true, calls the login() method in the Validation class
            try{
                Validation.signup(username, password);
                NurseDatabase.createNurseObject(scan, username);
                fileManagement.createFolder(nursePath, username);
                homepage(scan, username);
            } catch (AccountExistingException a){
                System.out.println(a.getMessage());
                System.out.println();
            } catch (FolderCreationException e){
                System.out.println(e.getMessage());
                System.out.println();
            }
        } else {
            System.out.println("Input must be alphanumeric only");
        }
    }

    public static void homepage(Scanner scan, String username){
        Nurse nurse = NurseDatabase.getNurseInformation().get(username);
        boolean run = true;

        while (run) {
            System.out.println("What do you like to see?");
            System.out.println("A. Nurse's Information");
            System.out.println("B. Patient's Information Management");
            System.out.println("C. Diagnoses Page");
            System.out.println("D. Symptoms Page");
            System.out.println("E. New Diagnoses");
            System.out.println("F. New Symptoms");
            System.out.println("X. Log out");
            System.out.print("Enter your choice: ");
            String response = scan.nextLine().toUpperCase();
            System.out.println();
            
            if(!response.isEmpty()){
                char choice = response.charAt(0);
                switch (choice) {
                    case 'A':
                        Nurse.nurseInformationPage(scan, nurse);
                        break;
                    case 'B':
                        PatientManagement.patientInformationManagement(scan, nurse);
                        break;
                    case 'C':
                        Search.chooseDiagnosis(scan);
                        break;
                    case 'D':
                        Search.chooseSymptomList(scan); 
                        break;                   
                    case 'E':
                        DiagnosesDatabase.createNewDiagnosis(scan);
                        break;
                    case 'F':
                        SymptomDatabase.createNewSymptom(scan);
                        break;
                    case 'X':
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please try again.");
                        System.out.println();
                        break;
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void adminHome(Scanner scan){
        while (true) {
            System.out.println("\nAdmin Home");
            System.out.println("A. Show Accounts");
            System.out.println("B. Show Diagnoses");
            System.out.println("C. Show Symptoms");
            System.out.println("D. Show Patients");
            System.out.println("E. Show Nurses");
            System.out.println("F. Logout");
            System.out.print("Enter your choice: ");
            String choice = scan.nextLine().trim();
            
            if(!choice.isEmpty()){
                char response = choice.toUpperCase().charAt(0);
                switch (response) {
                    case 'A':
                        AccountsDatabase.showAccounts();
                        System.out.print("\nEnter to continue...");
                        scan.nextLine();
                        System.out.println();
                        break;
                    case 'B':
                        Search.chooseDiagnosis(scan);                    
                        break;
                    case 'C':
                        Search.chooseSymptomList(scan);
                        break;
                    case 'D':
                        System.out.println();
                        Map<String, TreeMap<String, Patient>> patientList = PatientDatabase.getNursePatients();
                        
                        System.out.printf("%-10s | %-15s | %-5s | %-5s | %-10s \n", 
                        "PATIENT ID", "NAME", "AGE", "SEX", "DIAGNOSIS");
                        
                        for(Map.Entry<String, TreeMap<String, Patient>> nurse: patientList.entrySet()){
                            for(Map.Entry<String, Patient> patients: nurse.getValue().entrySet()){
                                Patient patient = patients.getValue();
                                System.out.printf("%-10s | %-15s | %-5d | %-5s | %-10s \n", 
                                patient.getPatientId(), 
                                patient.getName(), patient.getAge(), patient.getSex(), patient.getDiagnosisName());
                            }
                        }
                        System.out.print("\nEnter to continue...");
                        scan.nextLine();
                        System.out.println();
                        break; 
                    case 'E':
                        System.out.println();
                        Map<String, Nurse> nurseList = NurseDatabase.getNurseInformation();
                        System.out.printf("%-10s | %-15s | %-5s | %-5s | %-10s | %-15s | %-10s \n", 
                        "NURSE ID", "NAME", "AGE", "SEX", "POSITION", "SHIFT SCHEDULE", "AREA ASSIGNMENT");
                        
                        for(Map.Entry<String, Nurse> nurses: nurseList.entrySet()){
                            Nurse nurse = nurses.getValue();
                            System.out.printf("%-10s | %-15s | %-5d | %-5s | %-10s | %-15s | %-10s \n",
                            nurse.getUserID(), nurse.getNurseName(), nurse.getAge(), nurse.getSex(), nurse.getPosition(), nurse.getShiftSchedule(), nurse.getAreaAssignment()
                            );
                        }
                        System.out.print("\nEnter to continue...");
                        scan.nextLine();
                        System.out.println();
                        break;
                    case 'F':
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }            
        }
    }
}
