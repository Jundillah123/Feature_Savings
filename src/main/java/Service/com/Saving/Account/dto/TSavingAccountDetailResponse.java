package Service.com.Saving.Account.dto;

import Service.com.Saving.Account.entity.MUser;
import Service.com.Saving.Account.entity.RStatus;
import Service.com.Saving.Account.entity.TSavingAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TSavingAccountDetailResponse {
    private String savingAccountDetail;
    private TSavingAccount savingAccountId;
    private BigDecimal nominal;
    private String mutation;
    private String destAccountNumber;
    private BigDecimal endBalance;
    private BigDecimal balance;
    private String referenceCode;
    private Timestamp createdAt;

    private MUser userIdDetail;
    private MUser userIdAuthorizationDetail;
    private RStatus statusIdDetail;
}
