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
        return clientService.saveItem(client);
    }

    @GetMapping("/clients")
    public List<Client> findAllClients() {
        return clientService.getItems();
    }

    @GetMapping("/clients/{id}")
    public Client findClientById(@PathVariable int id){
        return clientService.getItemsById(id);
    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable int id) {
        return clientService.deleteItem(id);
    }

    @PostMapping("/account/add")
    public BankAccount addAccount (BankAccount account) {
        return accountService.saveItem(account);
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAccounts() {
        return accountService.getItems();
    }

    @DeleteMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable int id) {
        return accountService.deleteItem(id);
    }
}
