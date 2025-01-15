package Service.com.Saving.Account.repository;


import Service.com.Saving.Account.entity.TJournalLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TJournalLedgerRepository extends JpaRepository<TJournalLedger, String> {

    @Query(value = "select * from t_journal_ledger tjl where tjl.id =:id ", nativeQuery = true)
    Optional<TJournalLedger> findByIdd (String id);
}
