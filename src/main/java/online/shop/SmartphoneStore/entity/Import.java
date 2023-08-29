package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "nhap_hang"
)
public class Import {
    @Id
    @GeneratedValue
    @Column(name = "ma_cung_cap")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ma_ncc")
    private Supplier supplier;

    @NotNull
    @Column(name = "tong_gia_tien")
    private Double total;

    @NotNull
    @CreationTimestamp
    @Column(name = "ngay_cung_cap")
    private LocalDateTime createAt;
}
