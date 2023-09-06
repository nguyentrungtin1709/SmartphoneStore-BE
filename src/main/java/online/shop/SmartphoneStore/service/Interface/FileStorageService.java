package online.shop.SmartphoneStore.service.Interface;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileStorageService {

    String uploadFile(MultipartFile file) throws IOException;

    void removeFile(UUID uuid);

    Resource getFile(UUID uuid) throws IOException;

}
