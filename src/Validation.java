public class Validation {
<<<<<<< HEAD
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
=======
    
    public static boolean isAlphanumeric(String username, String password) { 
        if (username.matches("[a-zA-Z0-9]+") && password.matches("[a-zA-Z0-9]+")) return true;
        else return false;
    }
    
    public static void login(String username, String password) {
        if (AccountsDatabase.getAccounts().containsKey(username)) {
            if (AccountsDatabase.getAccounts().get(username).equals(password)) {
                System.out.println("\nLog-in Successful!\n");
                if(username.equals(AccountsDatabase.getAdminName()) && password.equals(AccountsDatabase.getAdminPassword())){
                    System.out.println("Welcome to Admin Page");
                    // Menu.adminHome(username, password);
                } else {
                    System.out.println("Welcome!");
                    // Menu.nurseHome(username, password);
                }
            } else {
                System.out.println("\nIncorrect password!\n");
            }
        } else {
            System.out.println("\nUser not found!\n");
        }      
    }

    public static void signup(String username, String password) {
        if (!AccountsDatabase.getAccounts().containsKey(username)) {
            AccountsDatabase.addAccount(username, password);
            System.out.println("\nSign-up successful!\n");
        } else {
            System.out.println("\nUser already exists!\n");
        } 
    }
>>>>>>> 60c248193117aff93bc899d9eb8a29947ad7d4fb
}