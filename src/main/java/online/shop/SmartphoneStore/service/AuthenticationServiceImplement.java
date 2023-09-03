package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.Role;
import online.shop.SmartphoneStore.entity.Request.JsonWebTokenResponse;
import online.shop.SmartphoneStore.entity.Request.LoginRequest;
import online.shop.SmartphoneStore.entity.Request.RegisterRequest;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public JsonWebTokenResponse register(RegisterRequest request) {
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
                accountDetailsService.createAccount(account)
        );
        return new JsonWebTokenResponse(token);
    }

    @Override
    public JsonWebTokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = jsonWebTokenService.generateToken(
                accountDetailsService
                        .readAccountByEmail(request.getEmail())
                        .orElseThrow()
        );
        return new JsonWebTokenResponse(
                token
        );
    }
}
