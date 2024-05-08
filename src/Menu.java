import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What are you here for?");
        System.out.println("A. Login");
        System.out.println("B. Sign Up");
        System.out.println("C. Exit");
        char value = scan.nextLine().toUpperCase().charAt(0);

        switch (value) {
            case 'A':
                loginMenu(scan);
                break;
                
            case 'B':
                signUpMenu(scan);
                break;

            case 'C':
                System.out.println("See you soon, our nurse!");
                break;
            default:
                System.out.println("Invalid input. Please restart the program.");
                break;
        }
        scan.close();
    }

    public static void loginMenu(Scanner scan){
        System.out.println("Welcome back! Please enter the following to continue.");
        System.out.println("Username: ");
       // String username = scan.nextLine();
        System.out.println("Password: ");
       // String password = scan.nextLine();
       // Validation2.login(username, password);
    }
    
    public static void signUpMenu(Scanner scan){
        System.out.println("Welcome! Please enter the following to continue.");
        System.out.println("Desired username: ");
        // String username = scan.nextLine();
        System.out.println("Desired password: ");
        // String password = scan.nextLine();
        // Validation2.signUp(username, password);

    }
    }
