package atm_machine.DAO;

import atm_machine.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BankDAO<T> {

    private final Connection connection;
    private final Logger logger = LoggerFactory.getLogger(BankDAO.class);

    private static final Map<Class, String> TABLES = new HashMap<>();

    static {
        TABLES.put(Client.class, "client");
    }
    public BankDAO(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
}
