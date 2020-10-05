package atmmachine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class BankAccount {
    @Id
    @GeneratedValue
    private int accountId;

    @Column(name = "IBAN")
    private String IBAN;
    @Column(name = "PIN")
    private int PIN;
    @Column(name = "amount")
    private Double amount;

    @OneToOne
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client client;
}