package atmmachine.exception;

public class InvalidAmountError extends IllegalArgumentException {
    public InvalidAmountError(String message) {
        super(message);
    }
}
