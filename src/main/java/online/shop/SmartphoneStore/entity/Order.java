package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "don_hang"
)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ma_don_hang")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "ma_tai_khoan", nullable = false)
    private Account account;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private OrderStatus status;

    @NotNull
    @Positive
    @Column(name = "tong_gia_tien")
    private Double total;

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

    @NotNull
    @CreationTimestamp
    @Column(name = "ngay_dat_hang")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderItemList;
}
