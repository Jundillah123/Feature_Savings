package Service.com.Saving.Account.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_coa", schema = "public")
public class MCoa {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
}
