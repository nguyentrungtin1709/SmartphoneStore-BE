package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.Star;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "danh_gia",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "unique_tk_dh_dt",
                    columnNames = {
                            "ma_tai_khoan",
                            "ma_don_hang",
                            "ma_dien_thoai"
                    }
            )
        }
)
public class Rating {

    @Id
    @GeneratedValue
    @Column(name = "ma_danh_gia")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "ma_tai_khoan",
            nullable = false
    )
    private Account account;

    @ManyToOne
    @JoinColumn(
            name = "ma_don_hang",
            nullable = false
    )
    private Order order;

    @ManyToOne
    @JoinColumn(
            name = "ma_dien_thoai",
            nullable = false
    )
    private Smartphone smartphone;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "so_sao")
    private Star star;

    @Column(name = "nhan_xet")
    private String comment;

    @CreationTimestamp
    @Column(name = "ngay_danh_gia")
    private LocalDateTime createdAt;
}
