import atmmachine.ATM_Machine;
import atmmachine.model.BankAccount;
import atmmachine.model.Client;
import atmmachine.model.SimplifiedAccount;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = ATM_Machine.class)
public class ATMMachine_IT {


    private static BankAccount account1;
    private static BankAccount account2;
    private static BankAccount account3;
    private static SimplifiedAccount accountS1;
    private static SimplifiedAccount accountS2;
    private static SimplifiedAccount accountS3;
    private static Client client1;
    private static Client client2;
    private static Client client3;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void setUpBeforeClass() {
        account1 = new BankAccount(1, "iban1", 0000, 1.0, "username1", false, false, false, false, null, null);
        account2 = new BankAccount(2, "iban2", 0000, 1.0, "username2", false, false, false, false, null, null);
        account3 = new BankAccount(3, "iban3", 0000, 1.0, "username3", false, false, false, false, null, null);

        accountS1 = new SimplifiedAccount("iban1", 0.0, new Client());
        accountS2 = new SimplifiedAccount("iban2", 0.0, new Client());
        accountS3 = new SimplifiedAccount("iban3", 0.0, new Client());

        client1 = new Client(1, "name1", "lastName1");
        client2 = new Client(2, "name2", "lastName2");
        client3 = new Client(3, "name3", "lastName3");
    }

    @Test
    public void getAllAccounts_IT() {
        ResponseEntity<SimplifiedAccount[]> result = this.restTemplate
                .getForEntity("http://localhost:" + port + "/accounts", SimplifiedAccount[].class);

        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), is(notNullValue()));
    }

    @Test
    public void addClient_IT() {

        HttpEntity<Client> request = new HttpEntity<>(client1);
        ResponseEntity<Client> response = restTemplate.postForEntity("http://localhost:"+port+"/clients/add", request, Client.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().getFirstName(), is("name1"));
        restTemplate.delete("http://localhost:"+port+"/products/"+response.getBody().getClientId());
    }
}
