package Service.com.Saving.Account.repository;

import Service.com.Saving.Account.entity.MCoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MCoaRepository  extends JpaRepository<MCoa, String> {

    @Query(value = "select * from m_coa mc where mc.code =:code ", nativeQuery = true)
    Optional<MCoa> findByCode (String code);
}
