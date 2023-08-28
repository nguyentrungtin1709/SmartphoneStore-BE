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
        name = "hang_san_xuat"
)
public class Brand {
    @Id
    @GeneratedValue
    @Column(name = "ma_hang")
    private Integer id;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "ten_hang")
    private String name;

}
