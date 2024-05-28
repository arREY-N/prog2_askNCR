public class Validation {
    
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
                    Menu.nurseHome(username);
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
            Nurse.createNurseObject(username);
        } else {
            System.out.println("\nUser already exists!\n");
        } 
    }
}