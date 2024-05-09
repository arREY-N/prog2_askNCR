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

Nurse.java
    Class used to create new Nurse objects, holding vital information and functions to allow nurses to navigate through the system.