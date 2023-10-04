package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import online.shop.SmartphoneStore.entity.Enum.PaymentMethods;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private OrderStatus status;

    @Positive
    @Column(name = "tong_gia_tien", nullable = false)
    private Integer total;

    @Column(name = "thanh_pho", nullable = false)
    @Length(max = 50)
    private String city;

    @Column(name = "quan_huyen", nullable = false)
    @Length(max = 50)
    private String district;

    @Column(name = "xa_phuong", nullable = false)
    @Length(max = 50)
    private String commune;

    @Column(name = "so_nha", nullable = false)
    private String addressDetails;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "thanh_toan", nullable = false)
    private PaymentMethods paymentMethods;

    @CreationTimestamp
    @Column(name = "ngay_dat_hang")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderDetails> orderItemList = new ArrayList<>();
}
