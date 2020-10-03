package atmmachine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue
    private int clientId;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
}