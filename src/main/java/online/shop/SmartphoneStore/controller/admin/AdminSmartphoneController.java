package online.shop.SmartphoneStore.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import online.shop.SmartphoneStore.service.SmartphoneServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/admin/smartphones")
public class AdminSmartphoneController {

    private final SmartphoneService smartphoneService;

    @Autowired
    public AdminSmartphoneController(SmartphoneServiceImplement smartphoneService) {
        this.smartphoneService = smartphoneService;
    }

    @PostMapping
    public ResponseEntity<Smartphone> saveSmartphone(
        @RequestBody Smartphone smartphone
    ) throws UniqueConstraintException, DataNotFoundException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.saveSmartphone(smartphone)
                );
    }

    @DeleteMapping("/{smartphoneId}")
    public ResponseEntity<Object> deleteSmartphoneById(
            @PathVariable("smartphoneId") Long smartphoneId
    ) throws DataNotFoundException, IOException {
        smartphoneService.deleteSmartphoneById(smartphoneId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }

    @PutMapping("/{smartphoneId}/image")
    public ResponseEntity<Smartphone> updateImage(
            @PathVariable("smartphoneId") Long smartphoneId,
            @RequestParam("image") MultipartFile image,
            HttpServletRequest request
    ) throws DataNotFoundException, IOException {
        int index = request.getRequestURL().indexOf("v1") + 2;
        String imagePath = request.getRequestURL().subSequence(0, index).toString().concat("/resources/");
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.updateImage(smartphoneId, image, imagePath)
                );
    }

    @PutMapping("/{smartphoneId}/info")
    public ResponseEntity<Smartphone> updateInfo(
            @PathVariable("smartphoneId") Long smartphoneId,
            @Valid @RequestBody Smartphone smartphone
    ) throws DataNotFoundException, UniqueConstraintException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.updateInfo(smartphoneId, smartphone)
                );
    }

    @GetMapping("/number-of-smartphones")
    public ResponseEntity<Map<String, Long>> numberOfSmartphones(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    smartphoneService.countAllSmartphones()
                );
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<List<Smartphone>> findBestSellers(
            @RequestParam(value = "top", defaultValue = "10") Integer top
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.findBestSellers(top)
                );
    }

    @GetMapping("/number-of-smartphones-by-brand")
    public ResponseEntity<List<Map<String, String>>> numberOfSmartphonesByBrand(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.countAllSmartphonesByBrand()
                );
    }

}
