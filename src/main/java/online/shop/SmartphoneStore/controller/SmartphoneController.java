package online.shop.SmartphoneStore.controller;

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
            @RequestParam(value = "p", defaultValue = "0") Integer page
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.readSmartphones(page)
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

}
