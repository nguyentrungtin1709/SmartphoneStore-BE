package online.shop.SmartphoneStore.controller;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.payload.PasswordChanging;
import online.shop.SmartphoneStore.service.AccountDetailsService;
import online.shop.SmartphoneStore.service.AuthenticationServiceImplement;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/account/profile")
public class AccountProfileController {

    private final AccountDetailsService accountDetailsService;

    private final AuthenticationService authenticationService;

    public AccountProfileController(
            AccountDetailsService accountDetailsService,
            AuthenticationServiceImplement authenticationService
    ) {
        this.accountDetailsService = accountDetailsService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Account> readProfile(
            @CurrentSecurityContext SecurityContext securityContext
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(account);
    }

    @PutMapping("/avatar")
    public ResponseEntity<Account> updateImage(
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestParam("avatar") MultipartFile file
    ) throws IOException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        accountDetailsService.updateAvatar(
                                account.getEmail(),
                                file
                        )
                );
    }

    @PutMapping("/password")
    public ResponseEntity<Object> changePassword(
            @CurrentSecurityContext SecurityContext securityContext,
            @Valid @RequestBody PasswordChanging passwordChanging
    ) {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        authenticationService.changePassword(account, passwordChanging);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }
}
