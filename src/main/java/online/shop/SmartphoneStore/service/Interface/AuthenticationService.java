package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Request.ChangePasswordRequest;
import online.shop.SmartphoneStore.entity.Request.JsonWebTokenResponse;
import online.shop.SmartphoneStore.entity.Request.LoginRequest;
import online.shop.SmartphoneStore.entity.Request.RegisterRequest;

public interface AuthenticationService {
    JsonWebTokenResponse register(RegisterRequest request);

    JsonWebTokenResponse login(LoginRequest request);

    void changePassword(Account account, ChangePasswordRequest request);
}
