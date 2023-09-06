package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tap_tin"
)
public class FileStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_tap_tin")
    private UUID uuid;

    @NotNull
    @Column(name = "ten_tap_tin")
    private String name;

    @NotNull
    @Column(name = "kieu_tap_tin")
    private String contentType;

    @NotNull
    @Column(name = "kich_thuoc")
    private Long size;
}
