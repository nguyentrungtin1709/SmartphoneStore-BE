package online.shop.SmartphoneStore.entity.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailChanging {
    @NotBlank(message = "Không được bỏ trống")
    @Email(message = "Email không hợp lệ")
    private String email;
}
