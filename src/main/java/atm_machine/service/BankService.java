package atm_machine.service;

import atm_machine.DAO.BankDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BankService<T> {
    static final Logger logger = LoggerFactory.getLogger(BankService.class);
    final BankDAO<T> dao;
    final ObjectMapper objectMapper;

    public BankService(ObjectMapper objectMapper, BankDAO<T> dao) {
        this.dao = dao;
        this.objectMapper = objectMapper;
    }
}
