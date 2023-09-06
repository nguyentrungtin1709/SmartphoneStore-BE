package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/api/v1/upload")
public class TestUploadFile {

    private final FileStorageService fileStorageService;

    @Autowired
    public TestUploadFile(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public String uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return fileStorageService.uploadFile(file);
    }

}
