public class Main{
    public static void main(String[] args)  {
        BackEnd backEndThread = new BackEnd();
        FrontEnd frontEndThread = new FrontEnd();

        backEndThread.start();
        try{
            backEndThread.join();
        } catch (InterruptedException e){
            System.out.println("Error in running database thread!");
        }      
        System.out.println();  
        frontEndThread.start();
    } 
}

class BackEnd extends Thread{
    @Override
    public void run() {
        try{
            System.out.println("Loading...");
            AccountsDatabase.loadFromFile();    // loads txt files into the program
            NurseDatabase.loadFromFile();
            DiagnosesDatabase.loadFromFile();
            NurseDatabase.loadFromFile();
            SymptomDatabase.loadFromFile();
            PatientDatabase.loadFromFile();
            Thread.sleep(3000);
        } catch (Exception e){
            System.out.println("Error loading database!");
            e.printStackTrace();
        }
    }
}

class FrontEnd extends Thread{
    @Override
    public void run() {
        try{
            System.out.println("Welcome to askNCR!\n");
            Menu.mainMenu();
        } catch (Exception e){
            System.out.println("Error loading menu!");
            e.printStackTrace();
        }
    }
}