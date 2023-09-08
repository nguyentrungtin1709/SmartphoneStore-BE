package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public interface FileStorageService {

    URI uploadFile(MultipartFile file) throws IOException;

    void removeFile(UUID uuid) throws IOException;

    Resource getFile(UUID uuid) throws IOException, DataNotFoundException;

}
