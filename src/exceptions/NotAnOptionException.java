package exceptions;

public class NotAnOptionException extends InputException {
    public NotAnOptionException(String msg){
        super("Not an option! Please try again.");
    }
}
