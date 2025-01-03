package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.RStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RStatusRespository extends JpaRepository<RStatus, String> {
    Optional<RStatus> findByStatusName(String statusName);
}
