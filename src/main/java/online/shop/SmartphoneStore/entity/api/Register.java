package online.shop.SmartphoneStore.entity.api;

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
public class Register {

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 100)
    private String name;

    @NotNull
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    private String email;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String password;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 10, max = 10, message = "Số điện thoại không hợp lệ")
    private String phone;
}
