package atmmachine.service;

import atmmachine.DAO.BankAccountDAO;
import atmmachine.DAO.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import atmmachine.model.Client;
import atmmachine.model.BankAccount;

import java.util.List;

@Service
public class BankService<T> {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private BankAccountDAO accountDAO;

    public Client saveClient(Client item) {
        return clientDAO.save(item);
    }

    public List<Client> getClients() {
        return clientDAO.findAll();
    }

    public Client getClientsById(int id) {
        return clientDAO.findById(id).orElse(null);
    }

    public String deleteClient(int id) {
        clientDAO.deleteById(id);
        return "Deleted client with id " + id;
    }

    public List<BankAccount> getAccounts() {
        return accountDAO.findAll();
    }

    public String deleteAccounts(int id) {
        accountDAO.deleteById(id);
        return "Account deleted. Id: " + id;
    }

    public int checKPIN(int pin) {
        return accountDAO.checkPINExistance(pin);
    }

    public double getAmount(int pin) { return accountDAO.getAmount(pin); }

    public boolean checkAmount(Integer amount, int pin) {
        return accountDAO.getAmount(pin) >= amount;
    }

    public void updateAccount(int pin, Double amount) {
        accountDAO.updateAmount(amount, pin);
    }
}
