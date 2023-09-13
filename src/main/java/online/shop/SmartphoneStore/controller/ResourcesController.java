package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.FileStorageServiceImplement;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourcesController {

    private final FileStorageService fileStorageService;

    @Autowired
    public ResourcesController(FileStorageServiceImplement fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> file(
            @PathVariable("fileId") UUID uuid
    ) throws IOException, DataNotFoundException {
        Resource resource = fileStorageService.getFile(uuid);
        String contentType = getContentType(resource.getDescription());
        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .body(resource);
    }

    private String getContentType(String description){
        return description.substring(
                description.indexOf("[") + 1,
                description.indexOf("]")
        );
    }

}
