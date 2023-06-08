package exceptions;

public class CustomExceptions extends Exception{
    public CustomExceptions(String message) {
        super(message);
    }
};

class InvalidArgument extends Exception {
    public InvalidArgument(String message) {
        super(message);
        }
};

class InvalidFormat extends Exception {
    public InvalidFormat(String message) {
        super(message);
    }
};

class NotPersonslistException extends Exception {
    public NotPersonslistException(String message) {
        super(message);
    }
};

