import atmmachine.controller.BankController;
import atmmachine.model.Client;
import atmmachine.service.BankService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClientControllerTest {

    private static Client client1;
    private static Client client2;
    private static Client client3;

    @Mock
    private BankService<Client> clientService;

    @InjectMocks
    private BankController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void setUpBeforeClass() {
        client1 = new Client(1, "name1", "lastName1");
        client2 = new Client(2, "name2", "lastName2");
        client3 = new Client(3, "name3", "lastName3");
    }

    @Test
    public void getClientsTest_whenNoRows() {
        when(clientService.getClients()).thenReturn(Arrays.asList());
        assertThat(controller.findAllClients().size(), is(0));
        Mockito.verify(clientService, Mockito.times(1)).getClients();
    }

    @Test
    public void getClientsTest() {
        when(clientService.getClients()).thenReturn(Arrays.asList(client1, client2, client3));
        assertThat(controller.findAllClients().size(), is(3));
        Mockito.verify(clientService, Mockito.times(1)).getClients();
    }

    @Test
    public void deleteClientById_WhenNotFound() {
        Mockito.when(clientService.deleteClient(1)).thenReturn(null);
        controller.deleteClient(1);
        Mockito.verify(clientService, Mockito.times(1)).deleteClient(1);
    }

    @Test
    public void deleteClientById_WhenFound() {

        Mockito.when(clientService.deleteClient(1)).thenReturn(String.valueOf(client1));
        controller.deleteClient(1);
        Mockito.verify(clientService, Mockito.times(1)).deleteClient(1);
    }

    @Test
    public void addClient() {
        Client client = clientService.saveClient(client2);
        Mockito.verify(clientService, Mockito.times(1)).saveClient(client2);
    }
}
