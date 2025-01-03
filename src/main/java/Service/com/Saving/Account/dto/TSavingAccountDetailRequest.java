package Service.com.Saving.Account.dto;


import Service.com.Saving.Account.entity.TSavingAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TSavingAccountDetailRequest {
    private TSavingAccount savingAccountId;
    private BigDecimal nominal;
    private String mutation;
    private String sourceAccountNumber;
    private String destAccountNumber;
    private BigDecimal endBalance;
    private BigDecimal balance;
    private String referenceCode;
}
