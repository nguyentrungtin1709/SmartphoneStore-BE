package online.shop.SmartphoneStore.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import online.shop.SmartphoneStore.entity.Enum.Sort;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import online.shop.SmartphoneStore.service.SmartphoneServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


@Validated
@RestController
@RequestMapping("/api/v1/smartphones")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;

    @Autowired
    public SmartphoneController(SmartphoneServiceImplement smartphoneService) {
        this.smartphoneService = smartphoneService;
    }

    @GetMapping
    public ResponseEntity<Page<Smartphone>> readSmartphones(
            @RequestParam(value = "p", defaultValue = "0") Integer page,
            @RequestParam(value = "brand", required = false) Integer brandId,
            @RequestParam(value = "min", required = false)
            @Min(value = 0) @Max(value = 100) Integer minPrice,
            @RequestParam(value = "max", required = false)
            @Min(value = 0) @Max(value = 1000) Integer maxPrice,
            @RequestParam(value = "sort", defaultValue = "0") Integer sort
        ){
        Map<String, Integer> price = convertMinAndMaxPriceFromData(minPrice, maxPrice);
        minPrice = price.get("minPrice");
        maxPrice = price.get("maxPrice");
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.readSmartphones(
                                page,
                                brandId,
                                minPrice,
                                maxPrice,
                                getSortFromInt(sort)
                        )
                );
    }

    @GetMapping("/{smartphoneId}")
    public ResponseEntity<Smartphone> readSmartphoneById(
            @PathVariable("smartphoneId") Long smartphoneId
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.readSmartphoneById(smartphoneId)
                );
    }

    private Sort getSortFromInt(Integer num){
        if (num == Sort.INCREASE.ordinal()){
            return Sort.INCREASE;
        }
        if (num == Sort.DECREASE.ordinal()){
            return Sort.DECREASE;
        }
        if (num == Sort.NEW.ordinal()){
            return Sort.NEW;
        }
        return Sort.NEW;
    }

    private Map<String, Integer> convertMinAndMaxPriceFromData(Integer minPrice, Integer maxPrice){
        if (Objects.isNull(minPrice) || Objects.isNull(maxPrice)){
            if (Objects.isNull(minPrice) && Objects.isNull(maxPrice)){
                minPrice = 0;
                maxPrice = Integer.MAX_VALUE;
            }
            else if (Objects.nonNull(minPrice)){
                minPrice *= 1_000_000;
                maxPrice = Integer.MAX_VALUE;
            } else {
                maxPrice *= 1_000_000;
                minPrice = 0;
            }
        }
        else if (minPrice >= maxPrice){
            minPrice = 0;
            maxPrice = Integer.MAX_VALUE;
        }
        else {
            minPrice *= 1_000_000;
            maxPrice *= 1_000_000;
        }
        return Map.of("minPrice", minPrice, "maxPrice", maxPrice);
    }
}
