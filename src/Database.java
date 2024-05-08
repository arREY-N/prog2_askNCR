import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static HashMap<String, String> accountList = new HashMap<>();
    
    public static void loadFromFile(){
        Path accountPath = Paths.get("database\\nurse\\nurseAccount.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(accountPath.toAbsolutePath().toString()))) {
            String delimiter = ",";
            String line;
            String[] credentials = null;

            while((line = reader.readLine()) != null){
                credentials = line.split(delimiter);
                accountList.put(credentials[0], credentials[1]);
            }        
        } catch (IOException e) {
            System.out.println("Nurse Accounts Not Found");
        }        
    }

    private static void showAccounts(){
        for(Map.Entry<String, String> account: accountList.entrySet()){
            System.out.println("Account: " + account.getKey());
            System.out.println("Password: " + account.getValue());
            System.out.println();
        }
    }

    public static HashMap<String, String> getAccounts(){
        return accountList;
    }
}