import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Validation {
    public static boolean isAlphanumeric(String username,String password) throws InvalidInputException{ 
        if (input != null && input.matches("[a-zA-Z0-9]+")){
            return true;
        } else {
            throw new InvalidInputException();
        }
    }
    
    public static void login(String username, String password) {
        if (!getCredentials().containsKey(username)) {
            System.out.println("User not found.");
        } else {
            if (!getCredentials().get(username).equals(password)) {
                System.out.println("Incorrect password.");
            } else {
                System.out.println("Login Successful.");
            }
        }      
    }

    public static void signin(String username, String password) {
        if (!isAlphanumeric(username) || !isAlphanumeric(password)) {
            System.out.println("Username and password must be alphanumeric.");
        }
        else{
            if (getCredentials().containsKey(username)) {
                System.out.println("User already exists.");
            }
        }
        else{
            getCredentials().put(username, password);
            saveCredentialsToFile(credentials);
            System.out.println("User [" + username + "] Signed-In successfully.");
        } 
    }
}

//if(Database.getAccounts().containsKey(username)){
//
//}