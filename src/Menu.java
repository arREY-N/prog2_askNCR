import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Menu {
    private static Path nursePath = Paths.get("database\\nurse");
    public static void main(String[] args) {
        AccountsDatabase.loadFromFile();    // loads txt files into the program
        NurseDatabase.loadFromFile();
        DiagnosesDatabase.loadFromFile();
        NurseDatabase.loadFromFile();
        SymptomDatabase.loadFromFile();

        Scanner scan = new Scanner(System.in);
        Boolean run = true;
        
        while(run == true){
            System.out.println("What are you here for?");
            System.out.println("A. Login");
            System.out.println("B. Sign Up");
            System.out.println("C. Exit");
            System.out.println("D: Choose symptom");
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
                    case 'C':    
                        run = false;
                        AccountsDatabase.loadToFile();
                        NurseDatabase.loadToFile();
                        System.out.println("See you soon, our nurse!\n");
                        break;
                    case 'D':
                        Search.chooseSymptoms(scan);
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
                fileManagement.createFile(username + "_patients.txt", nursePath.resolve(username));
                homepage(scan, username);
            } catch (AccountExistingException a){
                System.out.println(a.getMessage());
                System.out.println();
            } catch (FolderCreationException e){
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IOException e) {
                System.out.println("Error creating file\n");
            }
        } else {
            System.out.println("Input must be alphanumeric only");
        }
    }

    public static void homepage(Scanner scan, String username){
        Nurse nurse = NurseDatabase.getNurseAccounts().get(username);
        boolean run = true;

        while (run) {
            System.out.println("What do you like to see?");
            System.out.println("A. Nurse's Information");
            System.out.println("B. Patient's Information Management");
            System.out.println("C. Diagnoses Page");
            System.out.println("D. Symptoms Page");
            System.out.println("E. Log out");
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
                        Search.chooseSymptoms(scan); 
                        break;                   
                    case 'E':
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
            System.out.println("Admin Home");
            System.out.println("1. Show Accounts");
            System.out.println("2. Show Diagnoses");
            System.out.println("3. Show Patients");
            System.out.println("4. Show Symptoms");
            System.out.println("5. Show Nurses");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            scan.nextLine(); 
            switch (choice) {
                case 1:
                    AccountsDatabase.showAccounts();
                    break;
                case 2:
                    Retrieve.showAllDiagnose();
                    break;
                case 3:
                    // PatientDatabase.showPatientList();
                    break;
                case 4:
                    Retrieve.showAllSymptoms();
                    break;
                case 5:
                    NurseDatabase.getNurseAccounts();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
