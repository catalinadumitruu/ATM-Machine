package atmmachine.validation;

import atmmachine.exception.AmountMultiplicityException;
import atmmachine.exception.BalanceException;
import atmmachine.exception.InvalidAmountError;

public class BankValidator {

    public static boolean validateAmount(int amount) throws InvalidAmountError {
        if(amount < 50) {
            throw new InvalidAmountError("The specified amount is less than the minimum withdrawal amount");
        } else {
            return true;
        }
    }

    public static boolean checkRemainingAmount (double initialAmount, int amount) throws BalanceException {
        if( initialAmount - amount >= 50 ) {
            return true;
        } else {
            throw new BalanceException("Remaining amount is too low or your account doesn't have enough money.");
        }
    }

    public static boolean checkAmountMultiplicity( int amount) throws AmountMultiplicityException {
        if(amount % 10 == 0) {
            return true;
        } else {
            throw new AmountMultiplicityException("The given amount has to be divided by 10.");
        }
    }
}
