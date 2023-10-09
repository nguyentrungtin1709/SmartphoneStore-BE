package online.shop.SmartphoneStore.controller.customer;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Rating;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.RatingOverLimitException;
import online.shop.SmartphoneStore.service.Interface.RatingService;
import online.shop.SmartphoneStore.service.RatingServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/account/ratings")
public class AccountRatingController {

    private final RatingService ratingService;

    @Autowired
    public AccountRatingController(RatingServiceImplement ratingServiceImplement) {
        this.ratingService = ratingServiceImplement;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(
        @CurrentSecurityContext SecurityContext securityContext,
        @Valid @RequestBody Rating rating
    ) throws DataNotFoundException, RatingOverLimitException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.createRating(account, rating)
                );
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> editRatingOfAccount(
            @CurrentSecurityContext SecurityContext securityContext,
            @PathVariable("ratingId") Long ratingId,
            @Valid @RequestBody Rating rating
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.updateRating(ratingId, account, rating)
                );
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Map<String, String>> deleteRatingOfAccount(
            @CurrentSecurityContext SecurityContext securityContext,
            @PathVariable("ratingId") Long ratingId
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        ratingService.deleteRatingOfAccount(ratingId, account);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Map.of("message", "Đã xóa đánh giá")
                );
    }

    @GetMapping
    public ResponseEntity<Page<Rating>> readRatingsOfAccount(
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.readRatingsOfAccount(account, page)
                );
    }
}
