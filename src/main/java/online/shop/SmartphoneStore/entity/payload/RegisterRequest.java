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
public class RegisterRequest {

    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 50, message = "Tên không được vượt quá 50 kí tự")
    private String name;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-]).{8,}$",
            message = "Mật khẩu cần có tối thiểu 8 ký tự, bao gồm ít nhất một chữ hoa, chữ thường, kí tự và số."
    )
    private String password;

    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    private String phone;
}
