import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        AccountsDatabase.loadFromFile();    // loads txt files into the program
        Scanner scan = new Scanner(System.in);
<<<<<<< HEAD
        Database.loadFromFile();
        System.out.println("What are you here for?");
        System.out.println("A. Login");
        System.out.println("B. Sign Up");
        System.out.println("C. Exit");
        char value = scan.nextLine().toUpperCase().charAt(0);
=======
        Boolean run = true;
        
        while(run == true){
            System.out.println("What are you here for?");
            System.out.println("A. Login");
            System.out.println("B. Sign Up");
            System.out.println("C. Exit");
            String input = scan.nextLine().trim().toUpperCase();
>>>>>>> 60c248193117aff93bc899d9eb8a29947ad7d4fb

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
<<<<<<< HEAD
        System.out.println("Welcome back! Please enter the following to continue.");
        System.out.println("Username: ");
        String username = scan.nextLine();
        System.out.println("Password: ");
        String password = scan.nextLine();
        boolean valid = Validation.isAlphanumeric(username, password);

        if(valid==true){
            System.out.println("Valid Input");
            Validation.login(username,password);
        } else {
            System.out.println("Invalid Input");
        }
    }
    
    public static void signUpMenu(Scanner scan){
        System.out.println("Welcome! Please enter the following to continue.");
        System.out.println("Desired username: ");
        String username = scan.nextLine();
        System.out.println("Desired password: ");
        String password = scan.nextLine();
        //Validation.signup(username, password);
=======
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

    public static void adminHome(Scanner scan){
>>>>>>> 60c248193117aff93bc899d9eb8a29947ad7d4fb

    }

    public static void nurseHome(Scanner scan, String username, String password){

    }
<<<<<<< HEAD


class InvalidInputException extends Exception {
    public InvalidInputException(){
        super("Input must be alphanumeric");
    }
}
=======
}
>>>>>>> 60c248193117aff93bc899d9eb8a29947ad7d4fb
