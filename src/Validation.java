import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    
    public static boolean isAlphanumeric(String username, String password) { 
        if (username.matches("[0-9]{4}") && password.matches("[a-zA-Z0-9]+")) return true;
        else return false;
    }
    
    public static void login(String username, String password) throws InvalidCredentialsException{
        if (AccountsDatabase.getAccounts().containsKey(username)) {
            if (AccountsDatabase.getAccounts().get(username).equals(password)) {
                System.out.println("\nLog-in Successful!\n");
                if(username.equals(AccountsDatabase.getAdminName()) && password.equals(AccountsDatabase.getAdminPassword())){
                    System.out.println("Welcome to Admin Page");
                } else {
                    System.out.println("Welcome!\n");
                }
            } else {
                System.out.println("\nIncorrect password!");
                throw new InvalidCredentialsException();
            }
        } else {
            System.out.println("\nUser not found!");
            throw new InvalidCredentialsException();
        }      
    }

    public static void signup(String username, String password) throws AccountExistingException{
        if (!AccountsDatabase.getAccounts().containsKey(username)) {
                AccountsDatabase.addAccount(username, password);
        } else {
            System.out.println("\nUser already exists!");
            throw new AccountExistingException(); 
        } 
    }   
}