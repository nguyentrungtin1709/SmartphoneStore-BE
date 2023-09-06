package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.FileStorage;
import online.shop.SmartphoneStore.repository.FileStorageRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageServiceImplement implements FileStorageService {

    private final String FOLDER_PATH = "C:\\Users\\AD\\Desktop\\Image";

    private final String RESOURCES_URL = "/api/v1/resources/";

    private final FileStorageRepository fileStorageRepository;

    @Autowired
    public FileStorageServiceImplement(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        FileStorage fileStorage = fileStorageRepository.save(
          FileStorage
                  .builder()
                  .name(file.getOriginalFilename())
                  .contentType(file.getContentType())
                  .size(file.getSize())
                  .build()
        );
        String fileName = fileStorage
                .getUuid()
                .toString()
                .concat(
                        getExtensionFromFileName(
                                Objects.requireNonNull(file.getOriginalFilename())
                        )
                );
        file.transferTo(
            Path.of(
                    FOLDER_PATH.concat("\\").concat(fileName)
            )
        );
        return RESOURCES_URL.concat(
                fileStorage.getUuid().toString()
        );
    }

    @Override
    public void removeFile(UUID uuid) {

    }

    private String getExtensionFromFileName(String name){
        return name.substring(
                name.lastIndexOf(".")
        );
    }
}
