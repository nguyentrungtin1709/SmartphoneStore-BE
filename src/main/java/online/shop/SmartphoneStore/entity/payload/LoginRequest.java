package online.shop.SmartphoneStore.entity.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-]).{8,}$",
            message = "Mật khẩu cần có tối thiểu 8 ký tự, bao gồm ít nhất một chữ hoa, chữ thường, kí tự và số."
    )
    private String password;

}
