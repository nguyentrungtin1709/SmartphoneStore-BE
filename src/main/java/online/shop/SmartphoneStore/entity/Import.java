package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "ma_nhap_hang")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ma_ncc", nullable = false)
    private Supplier supplier;

    @NotNull
    @Positive
    @Column(name = "tong_gia_tien")
    private Double total;

    @OneToMany(mappedBy = "anImport")
    private List<ImportDetails> importDetailsList;

    @CreationTimestamp
    @Column(name = "ngay_nhap_hang")
    private LocalDateTime createdAt;
}
