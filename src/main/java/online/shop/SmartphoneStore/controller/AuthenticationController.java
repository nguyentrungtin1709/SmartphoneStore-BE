package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Request.ChangePasswordRequest;
import online.shop.SmartphoneStore.entity.Request.JsonWebTokenResponse;
import online.shop.SmartphoneStore.entity.Request.LoginRequest;
import online.shop.SmartphoneStore.entity.Request.RegisterRequest;
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
    public ResponseEntity<JsonWebTokenResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    authenticationService.register(request)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<JsonWebTokenResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        authenticationService.login(request)
                );
    }

    @PutMapping("/change-password")
    public ResponseEntity<RedirectView> changePassword(
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        authenticationService.changePassword(account, changePasswordRequest);
//        Chú ý ở điểm này
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new RedirectView("/api/v1/auth/login")
                );
    }
}
