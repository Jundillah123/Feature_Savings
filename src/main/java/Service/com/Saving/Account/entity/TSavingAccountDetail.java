package Service.com.Saving.Account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_saving_account_detail", schema = "public")
public class TSavingAccountDetail {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "saving_account_detail_id", nullable = false)
    private String savingAccountDetail;

    @ManyToOne
    @JoinColumn(name = "saving_account_id")
    private TSavingAccount savingAccountId;

    @Column(name = "nominal")
    private BigDecimal nominal;

    @Column(name = "mutation")
    private String mutation;

    @Column(name = "dest_account_number")
    private String destAccountNumber;

    @Column(name = "end_balance")
    private BigDecimal endBalance;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "reference_code")
    private String referenceCode;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private MUser userIdDetail;

    @ManyToOne
    @JoinColumn(name = "authorization_by")
    private MUser userIdAuthorizationDetail;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RStatus statusIdDetail;

}
