package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "hang_san_xuat",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_name",
                        columnNames = "ten_hang"
                )
        }
)
public class Brand {

    @Id
    @GeneratedValue
    @Column(name = "ma_hang")
    private Integer id;

    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "ten_hang", nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Smartphone> smartphoneList;

}
