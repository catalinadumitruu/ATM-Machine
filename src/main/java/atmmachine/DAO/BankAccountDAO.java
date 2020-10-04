package atmmachine.DAO;

import atmmachine.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountDAO extends JpaRepository<BankAccount, Integer> {

    @Query(value = "select count(*) from accounts acc where acc.pin = :pin", nativeQuery = true)
    int checkPINExistance(@Param("pin") int pin);
}