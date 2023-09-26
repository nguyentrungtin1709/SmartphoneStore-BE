package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.payload.PasswordChanging;
import online.shop.SmartphoneStore.entity.payload.TokenResponse;
import online.shop.SmartphoneStore.entity.payload.LoginRequest;
import online.shop.SmartphoneStore.entity.payload.RegisterRequest;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;

public interface AuthenticationService {
    TokenResponse register(RegisterRequest request) throws UniqueConstraintException;

    TokenResponse login(LoginRequest request);

    void changePassword(Account account, PasswordChanging request);

    TokenResponse changeEmail(String oldEmail, String newEmail) throws UniqueConstraintException;

}
