package by.lykianova.seohelper.error;


public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException(String message){
        super(message);
    }
}
