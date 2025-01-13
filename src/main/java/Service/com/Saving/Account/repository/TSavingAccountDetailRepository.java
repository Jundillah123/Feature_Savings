package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.TSavingAccount;
import Service.com.Saving.Account.entity.TSavingAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TSavingAccountDetailRepository extends JpaRepository<TSavingAccountDetail, String> {

    @Query(value = "select * from t_saving_account_detail tsad where tsad.saving_account_detail_id =:id", nativeQuery = true)
    Optional<TSavingAccountDetail> findByIdTSAD(String id);


}
