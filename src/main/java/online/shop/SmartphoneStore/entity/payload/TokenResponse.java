package online.shop.SmartphoneStore.entity.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.shop.SmartphoneStore.entity.Account;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private Account account;
    private String token;
}
