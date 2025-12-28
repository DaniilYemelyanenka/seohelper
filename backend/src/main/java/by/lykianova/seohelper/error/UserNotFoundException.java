package by.lykianova.seohelper.error;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long userID){
        super(String.format("User with id %d not found",userID));
    }
}
