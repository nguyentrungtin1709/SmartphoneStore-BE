package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.Role;
import online.shop.SmartphoneStore.entity.payload.PasswordChanging;
import online.shop.SmartphoneStore.entity.payload.TokenResponse;
import online.shop.SmartphoneStore.entity.payload.LoginRequest;
import online.shop.SmartphoneStore.entity.payload.RegisterRequest;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public TokenResponse register(RegisterRequest request) throws UniqueConstraintException {
        boolean hasEmail = accountDetailsService.wasRegisteredEmail(request.getEmail());
        boolean hasPhone = accountDetailsService.wasRegisteredPhone(request.getPhone());
        if (hasPhone || hasEmail){
            Map<String, String> columns = new HashMap<>();
            if (hasPhone){
                columns.put("phone", "Số điện thoại đã được đăng kí");
            }
            if (hasEmail){
                columns.put("email", "Email đã được đăng kí");
            }
            throw new UniqueConstraintException(columns);
        }
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
        account = accountDetailsService.saveAccount(account);
        String token = jsonWebTokenService.generateToken(account);
        return new TokenResponse(account, token);
    }

    @Override
    public TokenResponse register(RegisterRequest request, Role role) throws UniqueConstraintException {
        boolean hasEmail = accountDetailsService.wasRegisteredEmail(request.getEmail());
        boolean hasPhone = accountDetailsService.wasRegisteredPhone(request.getPhone());
        if (hasPhone || hasEmail){
            Map<String, String> columns = new HashMap<>();
            if (hasPhone){
                columns.put("phone", "Số điện thoại đã được đăng kí");
            }
            if (hasEmail){
                columns.put("email", "Email đã được đăng kí");
            }
            throw new UniqueConstraintException(columns);
        }
        Account account = Account
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .role(role)
                .phone(request.getPhone())
                .build();
        account = accountDetailsService.saveAccount(account);
        String token = jsonWebTokenService.generateToken(account);
        return new TokenResponse(account, token);
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Account account = (Account) accountDetailsService.loadUserByUsername(request.getEmail());
        String token = jsonWebTokenService.generateToken(account);
        return new TokenResponse(account, token);
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

    @Override
    public TokenResponse changeEmail(String oldEmail, String newEmail) throws UniqueConstraintException {
        Account account = (Account) accountDetailsService
                .loadUserByUsername(oldEmail);
        if (accountDetailsService.wasRegisteredEmail(newEmail) && !newEmail.equals(account.getEmail())){
            throw new UniqueConstraintException(Map.of("email", "Email đã tồn tại"));
        }
        account.setEmail(newEmail);
        return new TokenResponse(
                accountDetailsService.saveAccount(account),
                jsonWebTokenService.generateToken(account)
        );
    }
}
