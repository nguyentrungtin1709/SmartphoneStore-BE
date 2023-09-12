package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.net.URI;
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

    @NotBlank
    @Column(name = "ten_dien_thoai", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "ma_hang",
            nullable = false
    )
    private Brand brand;


    @Positive
    @Column(name = "gia_ban", nullable = false)
    private Double price;


    @PositiveOrZero
    @Column(name = "so_luong", nullable = false)
    private Integer quantityInStock;

    @Column(name = "man_hinh", length = 100)
    private String screen;

    @Column(name = "he_dieu_hanh", length = 100)
    private String operatingSystem;

    @Column(name = "camera_sau", length = 100)
    private String rearCamera;

    @Column(name = "camera_truoc", length = 100)
    private String frontCamera;

    @Column(name = "chip", length = 100)
    private String chip;

    @Column(name = "ram", length = 100)
    private String ram;

    @Column(name = "dung_luong", length = 100)
    private String storageCapacity;

    @Column(name = "sim", length = 100)
    private String sim;

    @Column(name = "pin_sac", length = 100)
    private String pin;

    @Column(name = "hinh_anh")
    private URI imageUrl;

    @NotBlank
    @Column(name = "sku", nullable = false)
    private String sku;

    @CreationTimestamp
    @Column(name = "ngay_them")
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "smartphone")
    private List<OrderDetails> orderDetailsList;

    @JsonIgnore
    @OneToMany(mappedBy = "smartphone")
    private List<Rating> ratingList;
}
