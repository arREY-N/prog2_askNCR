import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        AccountsDatabase.loadFromFile();    // loads txt files into the program
        DiagnosesDatabase.loadFromFile();
        Scanner scan = new Scanner(System.in);
        Boolean run = true;
        
        while(run == true){
            System.out.println("What are you here for?");
            System.out.println("A. Login");
            System.out.println("B. Sign Up");
            System.out.println("C. Exit");
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

    public static void adminHome(Scanner scan){

    }

    public static void nurseHome(Scanner scan, String username, String password){
        
    }
}
