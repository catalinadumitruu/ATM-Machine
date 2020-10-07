package atmmachine.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimplifiedAccount {
    private String IBAN;
    private double amount;
    private Client client;
}
