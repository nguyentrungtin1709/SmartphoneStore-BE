package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "ten_ncc")
    private String name;

    @NotNull
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 10)
    @Column(name = "so_dien_thoai")
    private String phone;

    @OneToMany(mappedBy = "supplier")
    private List<Import> supplyList;
}
