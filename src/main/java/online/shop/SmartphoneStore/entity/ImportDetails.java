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
        name = "chi_tiet_nhap_hang",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_mnh_sku",
                        columnNames = {"ma_nhap_hang", "sku"}
                )
        }
)
public class ImportDetails {

    @Id
    @GeneratedValue
    @Column(name = "ma_so")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "ma_nhap_hang",
            nullable = false
    )
    private Import anImport;

    @ManyToOne
    @JoinColumn(
            name = "sku",
            nullable = false,
            referencedColumnName = "sku"
    )
    private Smartphone smartphone;

    @NotNull
    @Positive
    @Column(name = "so_luong")
    private Integer quantity;

    @NotNull
    @Positive
    @Column(name = "gia_nhap")
    private Double price;
}
