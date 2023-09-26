package online.shop.SmartphoneStore.entity.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.Gender;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileChanging {

    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 50, message = "Tên không được vượt quá 50 kí tự")
    private String name;

    private LocalDate birthday;

    private Gender gender;

}
