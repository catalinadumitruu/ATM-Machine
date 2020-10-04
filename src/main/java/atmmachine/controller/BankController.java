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

//    @GetMapping("/accounts/checkPin/{pin}")
//    public int checkPin(@PathVariable int pin) {
//        return accountService.checKPIN(pin);
//    }

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
}
