package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "nha_cung_cap"
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
}
