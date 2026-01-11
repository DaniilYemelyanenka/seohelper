package by.lykianova.seohelper.error;

public class UserExistsException extends RuntimeException{

    public UserExistsException(String message){
        super(message);
    }
}
