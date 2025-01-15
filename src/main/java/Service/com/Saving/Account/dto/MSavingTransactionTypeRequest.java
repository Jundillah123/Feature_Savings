package Service.com.Saving.Account.dto;

import Service.com.Saving.Account.entity.MSaving;
import Service.com.Saving.Account.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MSavingTransactionTypeRequest {

    private String id;
    private TransactionType code;
    private MSaving savingId;
}
