package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "dia_chi"
)
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "ma_dia_chi")
    private Long id;

    @NotBlank
    @Length(max = 50)
    @Column(name = "thanh_pho", nullable = false)
    private String city;

    @NotBlank
    @Length(max = 50)
    @Column(name = "quan_huyen", nullable = false)
    private String district;


    @NotBlank
    @Length(max = 50)
    @Column(name = "xa_phuong", nullable = false)
    private String commune;

    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 255, message = "Địa chỉ chứa tối da 255 kí tự")
    @Column(name = "so_nha", nullable = false)
    private String addressDetails;

    @ManyToOne
    @JoinColumn(
            name = "ma_tai_khoan",
            nullable = false
    )
    @JsonIgnore
    private Account account;

}
