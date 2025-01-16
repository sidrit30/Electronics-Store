package Model.Exceptions;

public class InsufficientStockException extends Exception {
        public InsufficientStockException(String message) {
            super(message);
        }
    }