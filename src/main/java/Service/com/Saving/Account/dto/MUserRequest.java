package Service.com.Saving.Account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MUserRequest {
    private String email;
    private String userName;
    private String password;
    private String userRoleId;
    private String fullName;
    private String idNumber;
    private String idNumberType;
    private BigDecimal price;

}
