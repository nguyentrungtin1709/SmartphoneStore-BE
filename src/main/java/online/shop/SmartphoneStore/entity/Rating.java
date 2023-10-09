package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.Star;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

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
                    name = "unique_account_smartphone",
                    columnNames = {
                            "ma_tai_khoan",
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
            name = "ma_dien_thoai",
            nullable = false
    )
    private Smartphone smartphone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "so_sao", nullable = false)
    private Star star;

    @Length(max = 1000, message = "Đánh giá không được vượt quá 1000 ký tự")
    @Column(name = "nhan_xet", length = 1000)
    private String comment;

    @CreationTimestamp
    @Column(name = "ngay_danh_gia")
    private LocalDateTime createdAt;
}
