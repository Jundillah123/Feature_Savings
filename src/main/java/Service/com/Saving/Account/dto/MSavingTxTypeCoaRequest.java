package Service.com.Saving.Account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MSavingTxTypeCoaRequest {

    private String idSavingTxType;
    private String coaCode;
    private String mutation;
}
