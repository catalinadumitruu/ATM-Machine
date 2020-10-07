package atmmachine.validation;

import atmmachine.exception.AmountMultiplicityException;
import atmmachine.exception.BalanceException;
import atmmachine.exception.InvalidAmountError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankValidator {

    private final static Logger logger = LoggerFactory.getLogger(BankValidator.class);

    public static boolean validateAmount(int amount) {
        if(amount < 50) {
            logger.error("Specified amount is less than the 50 minimum withdrawal amount");
            throw new InvalidAmountError("The specified amount is less than the minimum withdrawal amount");
        } else {
            return true;
        }
    }

    public static boolean checkRemainingAmount (double initialAmount, int amount) {
        if( initialAmount - amount >= 50 ) {
            return true;
        } else {
            logger.error("Remaining amount is too low ore doesn't have enough money");
            throw new BalanceException("Remaining amount is too low or your account doesn't have enough money.");
        }
    }

    public static boolean checkAmountMultiplicity( int amount) {
        if(amount % 10 == 0) {
            return true;
        } else {
            logger.error("Given amount has to be divided by 10");
            throw new AmountMultiplicityException("The given amount has to be divided by 10.");
        }
    }
}
