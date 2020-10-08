import atmmachine.controller.BankController;
import atmmachine.model.BankAccount;
import atmmachine.model.Client;
import atmmachine.model.SimplifiedAccount;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountControllerTest {

    private static BankAccount account1;
    private static BankAccount account2;
    private static BankAccount account3;
    private static SimplifiedAccount accountS1;
    private static SimplifiedAccount accountS2;
    private static SimplifiedAccount accountS3;

    @Mock
    private BankService<BankAccount> accountService;

    @InjectMocks
    private BankController controller;

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
    }

    @Test
    public void getAccountsTest_whenNoRows() {
        when(accountService.getAccounts()).thenReturn(Arrays.asList());
        assertThat(controller.getAccounts().size(), is(0));
        Mockito.verify(accountService, Mockito.times(1)).getAccounts();
    }

    @Test
    public void getAccountsTest() {
        when(accountService.getAccountsSimplified()).thenReturn(Arrays.asList(accountS1, accountS2));
        assertThat(controller.getSimplifiedccounts().size(), is(2));
        Mockito.verify(accountService, Mockito.times(1)).getAccountsSimplified();
    }

    @Test
    public void findAccountById_exists() {
        Mockito.when(accountService.getAccountById(1)).thenReturn(account1);
        ResponseEntity<BankAccount> account = controller.findAccountById(1);
        assertThat(account.getBody(), is(account1) );
    }

    @Test
    public void findAccountById_notExists() {
        Mockito.when(accountService.getAccountById(1)).thenReturn(null);
        ResponseEntity<BankAccount> account = controller.findAccountById(1);
        assertThat(account.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void deleteAccountById_WhenNotFound() {
        Mockito.when(accountService.getAccountById(1)).thenReturn(null);
        controller.deleteAccount(1);
        Mockito.verify(accountService, Mockito.times(1)).deleteAccounts(1);
    }

    @Test
    public void deleteAccountById_WhenFound() {

        Mockito.when(accountService.deleteAccounts(1)).thenReturn(String.valueOf(account1));
        controller.deleteAccount(1);
        Mockito.verify(accountService, Mockito.times(1)).deleteAccounts(1);
    }
}
