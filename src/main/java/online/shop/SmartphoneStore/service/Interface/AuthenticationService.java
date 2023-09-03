package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Request.JsonWebTokenResponse;
import online.shop.SmartphoneStore.entity.Request.LoginRequest;
import online.shop.SmartphoneStore.entity.Request.RegisterRequest;
import online.shop.SmartphoneStore.service.JsonWebTokenService;

public interface AuthenticationService {
    JsonWebTokenResponse register(RegisterRequest request);

    JsonWebTokenResponse login(LoginRequest request);

}
