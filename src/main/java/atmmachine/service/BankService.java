package atmmachine.service;

import atmmachine.DAO.BankAccountDAO;
import atmmachine.DAO.ClientDAO;
import atmmachine.model.BankAccount;
import atmmachine.model.Client;
import atmmachine.model.SimplifiedAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService<T> {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private BankAccountDAO accountDAO;

    static final Logger logger = LoggerFactory.getLogger(BankService.class);

    public Client saveClient(Client item) {

        try{
            return clientDAO.save(item);
        } catch(Exception e) {
            logger.error("Something went wrong at saving client {}", item.toString());
            return null;
        }
    }

    public List<Client> getClients() {

        try{
            return clientDAO.findAll();
        } catch(Exception e) {
            logger.error("Couldn't get Clients.");
            return null;
        }
    }

    public Client getClientsById(int id) {

        try{
            return clientDAO.findById(id).orElse(null);
        }catch(Exception e) {
            logger.error("Couldn't get client with id {}", id);
            return null;
        }
    }

    public String deleteClient(int id) {

        try{
            clientDAO.deleteById(id);
            return "Deleted client with id " + id;
        } catch (Exception e) {
            logger.error("Client with id {} couldn't be delete. Error message: {}", id, e.getMessage());
            return "Something went wrong. Client was not deleted.";
        }
    }

    public List<BankAccount> getAccounts() {

        try{
            return accountDAO.findAll();
        } catch (Exception e) {
            logger.error("Couldn't get accounts");
            return null;
        }
    }

    public List<SimplifiedAccount> getAccountsSimplified() {

        try{
            List<BankAccount> list = accountDAO.findAll();
            List<SimplifiedAccount> simplifiedAccounts = new ArrayList<>();

            for(BankAccount account : list) {
                simplifiedAccounts.add(new SimplifiedAccount(account.getIBAN(), account.getAmount(), account.getClient()));
            }
            return simplifiedAccounts;
        } catch (Exception e) {
            logger.error("Couldn't get accounts");
            return null;
        }
    }

    public String deleteAccounts(int id) {

        try{
            accountDAO.deleteById(id);
            return "Account deleted. Id: " + id;
        } catch (Exception e) {
            logger.error("Account with id {} couldn't be delete. Error message: {}", id, e.getMessage());
            return "Something went wrong. Account was not deleted.";
        }
    }

    public BankAccount getAccountById(int id) {

        try{
            return accountDAO.findById(id).orElse(null);
        }catch(Exception e) {
            logger.error("Couldn't get account with id {}", id);
            return null;
        }
    }

    public int checkPIN(int pin) {
        return accountDAO.checkPINExistence(pin);
    }

    public double getAmount(int pin) { return accountDAO.getAmount(pin); }

    public boolean checkAmount(Integer amount, int pin) {
        return accountDAO.getAmount(pin) >= amount;
    }

    public void updateAccount(int pin, Double amount) {
        accountDAO.updateAmount(amount, pin);
    }

    public int getPin(String username) { return accountDAO.getPin(username); }
}
