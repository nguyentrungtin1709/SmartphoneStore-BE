package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.service.AccountDetailsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountDetailsService accountDetailsService;

    public AccountController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
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

    @PutMapping("/profile/avatar")
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
}
