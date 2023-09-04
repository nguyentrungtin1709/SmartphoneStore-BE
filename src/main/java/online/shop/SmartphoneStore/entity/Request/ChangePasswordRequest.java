package online.shop.SmartphoneStore.entity.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String oldPassword;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String newPassword;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String confirm;
}
