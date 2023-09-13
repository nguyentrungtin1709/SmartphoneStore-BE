package online.shop.SmartphoneStore.entity.payload;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChanging {

    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String oldPassword;

    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String newPassword;

    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    private String confirm;
}
