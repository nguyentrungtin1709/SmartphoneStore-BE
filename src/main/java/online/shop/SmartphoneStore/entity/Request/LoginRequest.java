package online.shop.SmartphoneStore.entity.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    private String email;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String password;

}
