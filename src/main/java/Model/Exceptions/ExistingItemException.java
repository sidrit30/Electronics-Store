package Model.Exceptions;

public class ExistingItemException extends RuntimeException {
    public ExistingItemException(String message) {
        super(message);
    }
}
