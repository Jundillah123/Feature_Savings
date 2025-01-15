package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.TSavingAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TSavingAccountRepository extends JpaRepository<TSavingAccount, String> {

@Query(value = "SELECT * FROM t_saving_account WHERE account_number = :accountNumber", nativeQuery = true)
    Optional<TSavingAccount> findByAccountNumber(String accountNumber);
}
