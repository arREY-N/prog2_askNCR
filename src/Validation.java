import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Validation {
    public static boolean isAlphanumeric(String username,String password){ 
        
        if(username.matches("[a-zA-Z0-9]+") && password.matches("[a-zA-Z0-9]+")) return true;
        else return false;
        
    }
    
    public static void login(String username, String password) {
        System.out.println(username);
        if ((Database.getAccounts()).containsKey(username)) {
            System.out.println("User found.");
            if ((Database.getAccounts().get(username).equals(password))){
                System.out.println("password correct.");
            } else {
                System.out.println("password incorrect");
            }
        } else {
            System.out.println("User not found.");
        }      
    }

    // public static void signup(String username, String password) {
    //     if (!isAlphanumeric(username) || !isAlphanumeric(password)) {
    //         System.out.println("Username and password must be alphanumeric.");
    //     }
    //     else{
    //         if (getCredentials().containsKey(username)) {
    //             System.out.println("User already exists.");
    //         }
    //     }
    //     else{
    //         getCredentials().put(username, password);
    //         saveCredentialsToFile(credentials);
    //         System.out.println("User [" + username + "] Signed-In successfully.");
    //     } 
    // }
}