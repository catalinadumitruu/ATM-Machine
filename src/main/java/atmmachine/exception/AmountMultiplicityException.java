package atmmachine.exception;

public class AmountMultiplicityException extends IllegalArgumentException{

    public AmountMultiplicityException(String message) {
        super(message);
    }
}
