package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MUserRepository extends JpaRepository<MUser, String> {
}
