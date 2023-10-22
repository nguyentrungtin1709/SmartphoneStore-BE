package online.shop.SmartphoneStore.controller.admin;

import online.shop.SmartphoneStore.entity.Rating;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.Interface.RatingService;
import online.shop.SmartphoneStore.service.RatingServiceImplement;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/ratings")
public class AdminRatingController {

    private final RatingService ratingService;

    public AdminRatingController(RatingServiceImplement ratingServiceImplement) {
        this.ratingService = ratingServiceImplement;
    }

    @GetMapping
    public ResponseEntity<Page<Rating>> readAllRatings(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.readAllRatings(page)
                );
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> readRatingById(
            @PathVariable("ratingId") Long ratingId
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.readRatingById(ratingId)
                );
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Map<String, String>> deleteRatingById(
            @PathVariable("ratingId") Long ratingId
    ) throws DataNotFoundException {
        ratingService.deleteRatingById(ratingId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    Map.of("message", "Đã xóa đánh giá")
                );
    }

    @GetMapping("/number-of-ratings")
    public ResponseEntity<Map<String, Long>> numberOfRatings(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    ratingService.countAllRatings()
                );
    }

    @GetMapping("/number-of-ratings-today")
    public ResponseEntity<Map<String, Long>> numberOfRatingsToday(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    ratingService.countAllRatingsToday()
                );
    }

    @GetMapping("/number-of-ratings-by-star")
    public ResponseEntity<Map<Integer, Long>> numberOfRatingsByStar(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ratingService.countRatingsByStar()
                );
    }
}
