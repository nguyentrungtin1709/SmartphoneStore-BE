package online.shop.SmartphoneStore.entity;

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

    @NotNull
    @NotBlank
    @Column(name = "thanh_pho")
    @Length(max = 50)
    private String city;

    @NotNull
    @NotBlank
    @Column(name = "quan_huyen")
    @Length(max = 50)
    private String district;

    @NotNull
    @NotBlank
    @Column(name = "xa_phuong")
    @Length(max = 50)
    private String commune;

    @NotNull
    @NotBlank
    @Column(name = "so_nha")
    private String addressDetails;

    @ManyToOne
    @JoinColumn(
            name = "ma_tai_khoan",
            nullable = false
    )
    private Account account;

}
