package by.itacademy.exception;

public class OperationException extends Exception {
    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationException(Throwable cause) {
        super(cause);
    }
}
