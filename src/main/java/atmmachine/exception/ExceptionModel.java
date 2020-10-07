package atmmachine.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionModel {
    private final String message;
    private final HttpStatus status;
    private final Date time;
}
