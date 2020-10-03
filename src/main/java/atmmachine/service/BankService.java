package atmmachine.service;

import atmmachine.DAO.BankDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService<T> {

    @Autowired
    private BankDAO<T> repository;


    public T saveItem(T item) {
        return repository.save(item);
    }

    public List<T> getItems() {
        return repository.findAll();
    }

    public T getItemsById(int id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteItem(int id) {
        repository.deleteById(id);
        return "Deleted client with id " + id;
    }
}
