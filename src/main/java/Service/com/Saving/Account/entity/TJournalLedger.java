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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_journal_ledger", schema = "public")
public class TJournalLedger {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "tx_code", referencedColumnName = "code")
    private TransactionType txCode;

    @ManyToOne
    @JoinColumn(name = "coa_code", referencedColumnName = "code")
    private MCoa coaCode;

    @Column(name = "mutation")
    private String mutation;

    @Column(name = "nominal")
    private BigDecimal nominal;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
