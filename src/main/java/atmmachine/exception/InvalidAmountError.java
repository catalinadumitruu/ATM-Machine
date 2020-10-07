package atmmachine.exception;

public class InvalidAmountError extends Exception {
    public InvalidAmountError(String message) {
        super(message);
    }
}
