package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.MSavingTransactionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MSavingTransactionTypeRepository extends JpaRepository <MSavingTransactionType, String> {
    @Query(value = "select * from m_saving_transaction_type mstt where mstt.id =:id ",
            nativeQuery = true)
    Optional<MSavingTransactionType> findByIdd (String id);


}
