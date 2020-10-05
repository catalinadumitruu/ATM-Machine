package atmmachine.controller;

import atmmachine.model.BankAccount;
import atmmachine.model.Client;
import atmmachine.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    private final BankService<Client> clientService;
    @Autowired
    private final BankService<BankAccount> accountService;

    public BankController(BankService<Client> clientService, BankService<BankAccount> accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    // basic functions with the classes

    @PostMapping("/clients/add")
    public Client addClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/clients")
    public List<Client> findAllClients() {
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public Client findClientById(@PathVariable int id){
        return clientService.getClientsById(id);
    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable int id) {
        return clientService.deleteClient(id);
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAccounts() {
        return accountService.getAccounts();
    }

    @DeleteMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable int id) {
        return accountService.deleteAccounts(id);
    }

    @GetMapping("/start")
    public String start(){
        return "Te rog introdu PIN-ul";
    }

    @PostMapping("/start")
    public String checkPin(@RequestBody String pin) {
        if(pin.matches("[0-9]+") && pin.length() == 4) {
            if (accountService.checKPIN(Integer.parseInt(pin)) >= 1) {
                return "PIN is valid, you are now connected! \n Please choose you next step: withdrawal, deposit or balance inquiry.";
            } else {
                return "We couldn't find your PIN.\nPlease contact the bank and create an account.";
            }
        } else {
            return "Please insert a valid PIN (it should contain only numbers).";
        }
    }

    @GetMapping("/balanceinquiry/{pin}")
    public String balanceInquiry(@PathVariable String pin) {
        if(pin.matches("[0-9]+") && pin.length() == 4) {
            if (accountService.checKPIN(Integer.parseInt(pin)) >= 1) {
                return "Available amount is " + accountService.getAmount(Integer.parseInt(pin));
            } else {
                return "Client was not found";
            }
        } else {
            return "Client's PIN is not in a valid form.";
        }
    }

    @PostMapping("/accounts/{pin}/withdrawal/{amount}")
    public String withdrawal(@PathVariable String pin,@PathVariable Double amount) {
        if(pin.matches("[0-9]+") && pin.length() == 4) {
            if (accountService.checKPIN(Integer.parseInt(pin)) >= 1) {
                if(accountService.checkAmount(amount, Integer.parseInt(pin))) {
                    accountService.updateAccount(Integer.parseInt(pin), accountService.getAmount(Integer.parseInt(pin)) - amount);
                    return "Withdrawal done! Current available sum is " + accountService.getAmount(Integer.parseInt(pin));
                } else {
                    return "Withdrawal cannot be terminated. Your account doesn't have enough money";
                }
            } else {
                return "Client was not found";
            }
        } else {
            return "Client's PIN is not in a valid form.";
        }
    }

    @PostMapping("/accounts/{pin}/deposit")
    public String deposit(@PathVariable String pin, @RequestBody Double amount) {
        if(pin.matches("[0-9]+") && pin.length() == 4) {
            if (accountService.checKPIN(Integer.parseInt(pin)) >= 1) {
                accountService.updateAccount(Integer.parseInt(pin), accountService.getAmount(Integer.parseInt(pin)) + amount);
                return "Deposit done. Current available sum is " + accountService.getAmount(Integer.parseInt(pin));
            } else {
                return "Client was not found";
            }
        } else {
            return "Client's PIN is not in a valid form.";
        }
    }
}
