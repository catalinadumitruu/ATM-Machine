package atmmachine.controller;

import atmmachine.exception.AmountMultiplicityException;
import atmmachine.exception.BalanceException;
import atmmachine.exception.InvalidAmountError;
import atmmachine.model.BankAccount;
import atmmachine.model.Client;
import atmmachine.model.SimplifiedAccount;
import atmmachine.service.BankService;
import atmmachine.validation.BankValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BankController {

    @Autowired
    private final BankService<Client> clientService;

    @Autowired
    private final BankService<BankAccount> accountService;

    private final Logger logger = LoggerFactory.getLogger(BankController.class);
    public static final int BIG_AMOUNT = 5000;

    public BankController(BankService<Client> clientService, BankService<BankAccount> accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping("/homeAdmin")
    public ModelAndView homeAdmin() {
        return new ModelAndView("homeAdmin");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/loginFailed")
    public ModelAndView tryAgain() {
        return new ModelAndView("loginFailed");
    }

    // basic functions with the classes (for admin user)

    @PostMapping("/clients/add")
    public Client addClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Client> findAllClients() {
        return clientService.getClients();
    }

    @GetMapping(value = "/bigAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankAccount> findBigAccounts() {
        List<BankAccount> accounts = accountService.getAccounts();
        return accounts.stream().filter(account -> account.getAmount() > BIG_AMOUNT).collect(Collectors.toList());
    }

    @GetMapping(value = "/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Client> findClientById(@PathVariable int id){

        List<Client> clients = clientService.getClients();
        return clients.stream().filter(client -> client.getClientId() == id).findFirst();
    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable int id) {
        return clientService.deleteClient(id);
    }

    @GetMapping("/accounts")
    public List<SimplifiedAccount> getAccounts() {
        List<BankAccount> accounts =  accountService.getAccounts();
        return accounts.stream().map(account -> new SimplifiedAccount(account.getIBAN(), account.getAmount(), account.getClient())).collect(Collectors.toList());
    }

    @DeleteMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable int id) {
        return accountService.deleteAccounts(id);
    }

    @GetMapping(value = "/balanceinquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    public String balanceInquiry(Principal principal) {
        int pin = accountService.getPin(principal.getName());

        if(String.valueOf(pin).matches("[0-9]+") && String.valueOf(pin).length() == 4) {
            if (accountService.checkPIN(pin) >= 1) {
                logger.info("Task -inquiry- successfully completed");
                return "Available amount is " + accountService.getAmount(pin);
            } else {
                logger.error("Client was not found.");
                return "Client was not found";
            }
        } else {
            logger.error("Client's PIN is not in a valid form.");
            return "Client's PIN is not in a valid form.";
        }
    }

    @GetMapping(value = "/withdrawal/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String withdrawal(@PathVariable Integer amount, Principal principal) {
        int pin = accountService.getPin(principal.getName());

        if(BankValidator.checkAmountMultiplicity(amount)) {
            if(BankValidator.validateAmount(amount)){
                if(BankValidator.checkRemainingAmount(accountService.getAmount(pin), amount)) {
                    if(accountService.checkAmount(amount, pin)) {
                        accountService.updateAccount(pin, accountService.getAmount(pin) - amount);
                        logger.info("Task -withdrawal- successfully completed");
                        return "Withdrawal done! Current available sum is " + accountService.getAmount(pin);
                    } else {
                        return "Withdrawal cannot be terminated. Your account doesn't have enough money";
                    }
                } else {
                    throw new BalanceException("Remaining amount is too low or your account doesn't have enough money");
                }
            } else {
                throw new InvalidAmountError("The specified amount is less than the minimum withdrawal amount (50 lei)");
            }
        } else {
            throw new AmountMultiplicityException("The given amount has to be divided by 10.");
        }
    }

    @GetMapping(value = "/deposit/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deposit(@PathVariable Integer amount, Principal principal) {
        int pin = accountService.getPin(principal.getName());

        if(BankValidator.checkAmountMultiplicity(amount)) {
            accountService.updateAccount(pin, accountService.getAmount(pin) + amount);
            logger.info("Task -deposit- successfully completed");
            return "Deposit done. Current available sum is " + accountService.getAmount(pin);
        } else {
            throw new AmountMultiplicityException("The given amount has to be divided by 10.");
        }
    }
}
