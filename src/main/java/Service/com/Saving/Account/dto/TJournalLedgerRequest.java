package Service.com.Saving.Account.dto;

import Service.com.Saving.Account.entity.MCoa;
import Service.com.Saving.Account.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TJournalLedgerRequest {

    private TransactionType txCode;
    private MCoa coaCode;
    private String mutation;
    private BigDecimal nominal;
    private String description;
    private Timestamp createdAt;
}
