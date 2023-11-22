package online.shop.SmartphoneStore.service.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface FileStorageService {

    URI uploadFile(MultipartFile file) throws IOException, URISyntaxException;

    void removeFile(String fileName) throws IOException, URISyntaxException;

}
