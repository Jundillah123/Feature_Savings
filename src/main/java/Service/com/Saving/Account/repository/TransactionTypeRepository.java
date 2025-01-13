package Service.com.Saving.Account.repository;


import Service.com.Saving.Account.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, String> {
    @Query(value = "select * from transaction_type tt where  tt.code =:code;", nativeQuery = true)
    Optional<TransactionType> findByCode (String code);
}
