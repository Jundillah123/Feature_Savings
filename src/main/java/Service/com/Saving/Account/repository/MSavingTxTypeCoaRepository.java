package Service.com.Saving.Account.repository;


import Service.com.Saving.Account.entity.MSavingTxTypeCoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSavingTxTypeCoaRepository extends JpaRepository<MSavingTxTypeCoa, String> {
}
