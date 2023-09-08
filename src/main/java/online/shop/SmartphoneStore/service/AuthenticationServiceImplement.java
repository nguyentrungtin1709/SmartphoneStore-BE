package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.Role;
import online.shop.SmartphoneStore.entity.payload.PasswordChanging;
import online.shop.SmartphoneStore.entity.payload.TokenResponse;
import online.shop.SmartphoneStore.entity.payload.LoginRequest;
import online.shop.SmartphoneStore.entity.payload.RegisterRequest;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class AuthenticationServiceImplement implements AuthenticationService {

    private final AccountDetailsService accountDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JsonWebTokenService jsonWebTokenService;

    public AuthenticationServiceImplement(
            AccountDetailsService accountDetailsService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JsonWebTokenService jsonWebTokenService
    ) {
        this.accountDetailsService = accountDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jsonWebTokenService = jsonWebTokenService;
    }

    @Override
    public TokenResponse register(RegisterRequest request) {
        Account account = Account
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .role(Role.CUSTOMER)
                .phone(request.getPhone())
                .build();
        String token = jsonWebTokenService.generateToken(
                accountDetailsService.saveAccount(account)
        );
        return new TokenResponse(token);
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = jsonWebTokenService.generateToken(
                (Account) accountDetailsService.loadUserByUsername(request.getEmail())
        );
        return new TokenResponse(token);
    }

    @Override
    public void changePassword(Account account, PasswordChanging request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getEmail(),
                        request.getOldPassword()
                )
        );
        account.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );
        accountDetailsService.saveAccount(account);
    }

}
