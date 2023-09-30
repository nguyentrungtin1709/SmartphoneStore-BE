package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Setter
@Getter
@Entity
@Builder
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
    @Length(max = 50, message = "Tên hãng không được vượt quá 50 ký tự")
    @Column(name = "ten_hang", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", cascade = {CascadeType.ALL})
    private List<Smartphone> smartphoneList;

}
