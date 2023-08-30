package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "dien_thoai",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_sku",
                        columnNames = "sku"
                ),
                @UniqueConstraint(
                        name = "unique_name",
                        columnNames = "ten_dien_thoai"
                )
        }
)
public class Smartphone {
    @Id
    @GeneratedValue
    @Column(name = "ma_dien_thoai")
    private Long id;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "ten_dien_thoai")
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "ma_hang",
            nullable = false
    )
    private Brand brand;

    @NotNull
    @Positive
    @Column(name = "gia_ban")
    private Double price;

    @NotNull
    @PositiveOrZero
    @Column(name = "so_luong")
    private Integer quantityInStock;

    @Column(name = "man_hinh")
    @Length(max = 100)
    private String screen;

    @Column(name = "he_dieu_hanh")
    @Length(max = 100)
    private String operatingSystem;

    @Column(name = "camera_sau")
    @Length(max = 100)
    private String rearCamera;

    @Column(name = "camera_truoc")
    @Length(max = 100)
    private String frontCamera;

    @Column(name = "chip")
    @Length(max = 100)
    private String chip;

    @Column(name = "ram")
    @Length(max = 100)
    private String ram;

    @Column(name = "dung_luong")
    @Length(max = 100)
    private String storageCapacity;

    @Column(name = "sim")
    @Length(max = 100)
    private String sim;

    @Column(name = "pin_sac")
    @Length(max = 100)
    private String pin;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "hinh_anh")
    private String imageUrl;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "sku")
    private String sku;

    @NotNull
    @CreationTimestamp
    @Column(name = "ngay_them")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "smartphone")
    private List<OrderDetails> orderDetailsList;

    @OneToMany(mappedBy = "smartphone")
    private List<Rating> ratingList;
}
