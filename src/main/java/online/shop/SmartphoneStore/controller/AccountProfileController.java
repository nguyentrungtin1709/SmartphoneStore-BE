package online.shop.SmartphoneStore.controller;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.payload.*;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
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

    @PutMapping("/email")
    public ResponseEntity<TokenResponse> changeEmail(
            @CurrentSecurityContext SecurityContext securityContext,
            @Valid @RequestBody EmailChanging emailChanging
    ) throws UniqueConstraintException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    authenticationService.changeEmail(account.getEmail(), emailChanging.getEmail())
                );
    }

    @PutMapping("/phone")
    public ResponseEntity<Account> changePhone(
            @CurrentSecurityContext SecurityContext securityContext,
            @Valid @RequestBody PhoneChanging phoneChanging
    ) throws UniqueConstraintException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        accountDetailsService.updatePhone(
                                account.getId(),
                                phoneChanging.getPhone()
                        )
                );
    }

    @PutMapping
    public ResponseEntity<Account> changeProfile(
            @CurrentSecurityContext SecurityContext securityContext,
            @Valid @RequestBody ProfileChanging profile
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        accountDetailsService.updateProfile(
                                account.getId(),
                                profile
                        )
                );
    }
}
