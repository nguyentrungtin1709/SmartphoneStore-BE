package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.Role;
import online.shop.SmartphoneStore.entity.api.PasswordChanging;
import online.shop.SmartphoneStore.entity.api.TokenResponse;
import online.shop.SmartphoneStore.entity.api.Login;
import online.shop.SmartphoneStore.entity.api.Register;
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

    private final FileStorageService fileStorageService;

    public AuthenticationServiceImplement(
            AccountDetailsService accountDetailsService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JsonWebTokenService jsonWebTokenService,
            FileStorageServiceImplement fileStorageService
    ) {
        this.accountDetailsService = accountDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jsonWebTokenService = jsonWebTokenService;
        this.fileStorageService = fileStorageService;
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

    @Override
    public Account updateAvatar(String email, MultipartFile file) throws IOException {
        Account account = accountDetailsService
                .readAccountByEmail(email)
                .orElseThrow();
        if (account.getImageUrl() != null){
            String path = account.getImageUrl().getPath();
            UUID uuid = UUID.fromString(
                    path.substring(
                            path.lastIndexOf("/") + 1
                    )
            );
            fileStorageService.removeFile(uuid);
        }
        URI imageUrl = fileStorageService.uploadFile(file);
        account.setImageUrl(imageUrl);
        return accountDetailsService.saveAccount(account);
    }
}
