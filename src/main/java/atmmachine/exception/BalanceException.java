package atmmachine.exception;

public class BalanceException extends IllegalArgumentException {

    public BalanceException(String message) {
        super(message);
    }
}
