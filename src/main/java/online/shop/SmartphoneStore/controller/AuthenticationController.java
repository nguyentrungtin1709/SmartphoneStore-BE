package online.shop.SmartphoneStore.controller;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.api.PasswordChanging;
import online.shop.SmartphoneStore.entity.api.TokenResponse;
import online.shop.SmartphoneStore.entity.api.Login;
import online.shop.SmartphoneStore.entity.api.Register;
import online.shop.SmartphoneStore.service.AuthenticationServiceImplement;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImplement authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(
            @Valid @RequestBody Register request
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    authenticationService.register(request)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody Login request
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        authenticationService.login(request)
                );
    }

    @PutMapping("/profile/password")
    public ResponseEntity<RedirectView> changePassword(
            @CurrentSecurityContext SecurityContext securityContext,
            @Valid @RequestBody PasswordChanging passwordChanging
    ) {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        authenticationService.changePassword(account, passwordChanging);
//      Notice
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new RedirectView("/api/v1/auth/login")
                );
    }

    @GetMapping("/profile")
    public ResponseEntity<Account> readProfile(
            @CurrentSecurityContext SecurityContext securityContext
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(account);
    }
}
