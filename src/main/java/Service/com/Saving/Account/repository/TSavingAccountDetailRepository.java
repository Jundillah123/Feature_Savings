package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.TSavingAccount;
import Service.com.Saving.Account.entity.TSavingAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TSavingAccountDetailRepository extends JpaRepository<TSavingAccountDetail, String> {



}
