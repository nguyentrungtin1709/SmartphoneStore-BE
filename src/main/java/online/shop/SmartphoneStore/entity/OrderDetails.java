package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "chi_tiet_don_hang",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "unique_order_item",
                    columnNames = { "ma_don_hang", "ma_dien_thoai" }
            )
        }
)
public class OrderDetails {

    @Id
    @GeneratedValue
    @Column(name = "ma_so")
    private Long id;

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
    @Positive
    @Column(name = "so_luong")
    private Integer quantity;

    @NotNull
    @Positive
    @Column(name = "gia_ban")
    private Double price;
}
