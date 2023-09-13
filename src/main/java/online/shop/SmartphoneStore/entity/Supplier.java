package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "nha_cung_cap",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_name",
                        columnNames = "ten_ncc"
                )
        }
)
public class Supplier {
    @Id
    @GeneratedValue
    @Column(name = "ma_ncc")
    private Integer id;

    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 50, message = "Tên không quá 50 kí tự")
    @Column(name = "ten_ncc", nullable = false)
    private String name;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "email", nullable = false)
    private String email;

    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    @Column(name = "so_dien_thoai", length = 10, nullable = false)
    private String phone;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<Import> supplyList;
}
