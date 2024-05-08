public class Validation {
    
    public static boolean isAlphanumeric(String username, String password) { 
        if (username.matches("[a-zA-Z0-9]+") && password.matches("[a-zA-Z0-9]+")) return true;
        else return false;
    }
    
    public static void login(String username, String password) {
        if (Database.getAccounts().containsKey(username)) {
            if (Database.getAccounts().get(username).equals(password)) {
                System.out.println("\nLog-in Successful!\n");
                if(username.equals(Database.getAdminName()) && password.equals(Database.getAdminPassword())){
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
        if (!Database.getAccounts().containsKey(username)) {
            Database.addAccount(username, password);
            System.out.println("\nSign-up successful!\n");
        } else {
            System.out.println("\nUser already exists!\n");
        } 
    }
}