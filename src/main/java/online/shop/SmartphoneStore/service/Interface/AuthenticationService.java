package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.api.PasswordChanging;
import online.shop.SmartphoneStore.entity.api.TokenResponse;
import online.shop.SmartphoneStore.entity.api.Login;
import online.shop.SmartphoneStore.entity.api.Register;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

public interface AuthenticationService {
    TokenResponse register(Register request);

    TokenResponse login(Login request);

    void changePassword(Account account, PasswordChanging request);

    Account updateAvatar(String email, MultipartFile file) throws IOException;
}
