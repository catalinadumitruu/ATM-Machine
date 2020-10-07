package atmmachine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "username")
    private String username = "client";

    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "accountExpired")
    private boolean accountExpired;

    @Column(name = "accountLocked")
    private boolean accountLocked;

    @Column(name = "credentialsExpired")
    private boolean credentialsExpired;

//    @Column(name = "role")
//    private String role;

    @OneToOne
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", IBAN='" + IBAN + '\'' +
                ", PIN=" + PIN +
                ", amount=" + amount +
                ", username='" + username + '\'' +
                ", disabled=" + disabled +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", credentialsExpired=" + credentialsExpired +
                ", client=" + client +
                ", roles=" + roles +
                '}';
    }
}