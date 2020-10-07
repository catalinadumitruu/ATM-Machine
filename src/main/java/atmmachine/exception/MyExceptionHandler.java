package atmmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(InvalidAmountError.class)
    public ResponseEntity<Object> handleInvalidAmountError(InvalidAmountError e) {
        ExceptionModel exceptionModel = new ExceptionModel(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<Object>  handleBalanceException(BalanceException e) {
        ExceptionModel exceptionModel = new ExceptionModel(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AmountMultiplicityException.class)
    public ResponseEntity<Object>  handleAmountMultiplicityException(AmountMultiplicityException e) {
        ExceptionModel exceptionModel = new ExceptionModel(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
