package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.Role;
import online.shop.SmartphoneStore.entity.api.PasswordChanging;
import online.shop.SmartphoneStore.entity.api.TokenResponse;
import online.shop.SmartphoneStore.entity.api.Login;
import online.shop.SmartphoneStore.entity.api.Register;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public TokenResponse register(Register request) {
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
    public TokenResponse login(Login request) {
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
        return new TokenResponse(
                token
        );
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
