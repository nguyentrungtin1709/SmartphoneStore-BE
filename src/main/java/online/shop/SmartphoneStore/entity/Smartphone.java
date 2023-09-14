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

    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "ten_dien_thoai", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "ma_hang",
            nullable = false
    )
    private Brand brand;


    @NotNull(message = "Không được bỏ trống")
    @Positive(message = "Giá bán cần lớn hơn 0")
    @Column(name = "gia_ban")
    private Integer price;



    @NotNull(message = "Không được bỏ trống")
    @PositiveOrZero(message = "Số lượng cần lớn hơn hoặc bằng 0")
    @Column(name = "so_luong")
    private Integer quantityInStock;

    @Column(name = "man_hinh")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String screen;

    @Column(name = "he_dieu_hanh")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String operatingSystem;

    @Column(name = "camera_sau")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String rearCamera;

    @Column(name = "camera_truoc")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String frontCamera;

    @Column(name = "chip")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String chip;

    @Column(name = "ram")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String ram;

    @Column(name = "dung_luong")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String storageCapacity;

    @Column(name = "sim")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String sim;

    @Column(name = "pin_sac")
    @Length(max = 100, message = "Chứa tối đa không quá 100 ký tự")
    private String pin;

    @Column(name = "hinh_anh")
    private URI imageUrl;

    @NotBlank(message = "Không được bỏ trống")
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
