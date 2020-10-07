package atmmachine.DAO;

import atmmachine.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BankAccountDAO extends JpaRepository<BankAccount, Integer> {

    @Query(value = "select count(*) from accounts acc where acc.pin = :pin", nativeQuery = true)
    int checkPINExistance(@Param("pin") int pin);

    @Query(value = "select amount from accounts where pin = :pin", nativeQuery = true)
    double getAmount(@Param("pin") int pin);

    @Query(value = "select * from accounts where pin = :pin", nativeQuery = true)
    Optional<BankAccount> findByPassword(@Param("pin") int pin);

    @Transactional
    @Modifying
    @Query(value = "update accounts set amount = :amount where pin = :pin", nativeQuery = true)
    void updateAmount(@Param("amount") Double amount, @Param("pin") int pin);

    Optional<BankAccount> findByUsername(String username);
}