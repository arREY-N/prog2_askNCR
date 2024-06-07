askNCR (2024)

    Penus, Reynal
    Caraig, Emmanuel Joseph
    Ortega, Zam
    Tagura, Donna Charisse
    Tan, Chloie
    CS202
    Computer Programming 2

askNCR is a system made to allow nurses to digitally navigate through resources to look for the appropriate care instructions for each symptoms that a patient has. It also serves as a database for nurses to easily monitor and save pertinent information regarding each patient.

Menu.java
    Contain the methods used in displaying menus/options
    
    main()
        Entry point of the program
        Display the login/signup options
    
    loginMenu(Scanner scan)
        Handles user login
        Receives a scanner parameter, allowing the same scanner to be used all throughout the program
    
    signupMenu(Scanner scan)
        Handles user sign up
        Receives a scanner parameter, allowing the same scanner to be used all throughout the program
    
    adminHome(Scanner scan)
        Used to display options for the admins
        Receives a scanner parameter, allowing the same scanner to be used all throughout the program
    
    nurseHome(Scanner scan)
        Used to display options for the nurses
        Receives a scanner parameter, allowing the same scanner to be used all throughout the program

Validation.java
    Contain methods used to run specific program functions

    isAlphanumeric(String username, String password)
        Ensures that user inputs are all alphanumeric

    login(String username, String password)
        Enables the user to login to their accounts

    signup(String username, String password)
        Enables users to create accounts in the program

Database.java
    Handles all the database functions of the program

    loadFromFile()
        Reads all the accounts in the file, then loads it to the program 

    loadToFile()
        Saves all new accounts in the program into the file
    
    getAccounts()
        Allows the list of accounts to be accessible throughout the program
    
    getAdminName()
        Returns the username of the Admin

    getAdminPassword()
        Returns the password of the Admin

    showAccounts()
        Displays all the nurse accounts in the database
        Only accessible in the admin account

Retrieve.java
    Contain methods to retrieve queried data from the program's database

Nurse.java
    Class used to create new Nurse objects, holding vital information and functions to allow nurses to navigate through the system.


database
    nurse
        nurseAccount.txt
            Contains the login credentials of users

        nurseInformation.txt
            Contains nurse's information
    
    patient
        [patient folder]
            patientInformation.txt
            patientCareRecommendation.txt
    
    symptom
        [Group]
            [symptom].txt
                Includes definition and care instructions    
    
    diagnosis
        [Group]
            [diagnosis].txt
                Includes definition and symptoms list

Refer to page 2 for all the symptom names
Refer to page 996 for all the diagnoses

add "======" to mark the end of the file



[SymptomDatabase.java]

VARIABLES
    symptomListFolderPath   path 
    symptomList             <(Symptom name), (Symptom object)>
    symptomArray            <(Symptom name)>    

METHODS
    loadFromFile()
        -   symptomsFiles   array containing all the symptoms within the symptoms folder
        
        reads all the files from the symptom folder
        iterates through all the files
        creates a BufferedReader to read the contents of each file

    getSymptomContents(String symptomName)
        - fileContents      map containing the contents of a symptom file

        returns the contents of a symptom file
    
    getSymptomList()
        returns the symptomArray (array of symptom names)

[DiagnosesDatabase.java]

VARIABLES
    dianosisListFolderPath      path, file containing all the diagnosis
    diagnosisNames              <(Diagnosis Number), (Diagnosis object)>
    diagnosisTable              <(Diagnosis Name), (Diagnosis number)>

METHODS
    loadFromFile()
        -   diagnosisFiles  array containinhg all the diagnoses within the diagnoes folder

        reads all the files from the diagnoses folder
        iterates through all the files
        creates a BufferedReader to read the contents of each file
        
    getDiagnosisContents(Integer diagnosisNumber)
        



[Search.java]

METHODS
    chooseDiagnosis(Scanner scan)
        displays all the diagnoses in the system
        asks the user to choose one