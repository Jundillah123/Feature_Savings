package Service.com.Saving.Account.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_saving_tx_type_coa", schema = "public")
public class MSavingTxTypeCoa {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name ="id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_saving_tx_type", referencedColumnName = "id")
    private MSavingTransactionType idSavingTxType;

    @ManyToOne
    @JoinColumn(name = "coa_code", referencedColumnName = "code")
    private MCoa coaCode;

    @Column(name = "mutation")
    private String mutation;


}
