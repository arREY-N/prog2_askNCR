class FolderCreationException extends Exception{
    FolderCreationException(){
        super("Error creating folder!");
    }   
}

class AccountExistingException extends Exception{
    AccountExistingException(){
        super("Account already exisitng!");
    }
}

class InvalidCredentialsException extends Exception{
    InvalidCredentialsException(){
        super("Log in Failed");
    }
}

class FileCreationException extends Exception{
    FileCreationException(){
        super("Error creating file!");
    }
}