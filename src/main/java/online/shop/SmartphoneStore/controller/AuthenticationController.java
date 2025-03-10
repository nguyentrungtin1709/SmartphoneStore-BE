package online.shop.SmartphoneStore.controller;

import jakarta.validation.Valid;

import online.shop.SmartphoneStore.entity.payload.TokenResponse;
import online.shop.SmartphoneStore.entity.payload.LoginRequest;
import online.shop.SmartphoneStore.entity.payload.RegisterRequest;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.AuthenticationServiceImplement;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Valid @RequestBody RegisterRequest request
    ) throws UniqueConstraintException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    authenticationService.register(request)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest request
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        authenticationService.login(request)
                );
    }

}
