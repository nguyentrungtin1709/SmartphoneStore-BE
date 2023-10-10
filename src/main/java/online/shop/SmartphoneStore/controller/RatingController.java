package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.entity.Rating;
import online.shop.SmartphoneStore.entity.payload.RatingStatistic;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.Interface.RatingService;
import online.shop.SmartphoneStore.service.RatingServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingServiceImplement ratingServiceImplement) {
        this.ratingService = ratingServiceImplement;
    }

    @GetMapping("/smartphone/{smartphoneId}")
    public ResponseEntity<Page<Rating>> readAllRatings(
            @PathVariable("smartphoneId") Long smartphoneId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.readAllRatingsOfSmartphone(smartphoneId, page)
                );
    }

    @GetMapping("/smartphone/{smartphoneId}/rate")
    public ResponseEntity<RatingStatistic> readRateValue(
            @PathVariable("smartphoneId") Long smartphoneId
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.readRateValue(smartphoneId)
                );
    }
}
