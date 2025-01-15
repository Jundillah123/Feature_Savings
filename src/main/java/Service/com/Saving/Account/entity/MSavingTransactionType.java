package Service.com.Saving.Account.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "m_saving_transaction_type", schema = "public")
public class MSavingTransactionType {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

   @ManyToOne
    @JoinColumn(name = "tx_code", referencedColumnName = "code")
    private TransactionType code;

   @ManyToOne
    @JoinColumn(name = "saving_id", referencedColumnName = "saving_id")
    private MSaving savingId;

}
