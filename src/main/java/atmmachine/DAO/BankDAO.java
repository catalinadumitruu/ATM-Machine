package atmmachine.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDAO<T> extends JpaRepository<T, Integer> {

}
