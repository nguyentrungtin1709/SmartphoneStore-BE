package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.FileStorage;
import online.shop.SmartphoneStore.repository.FileStorageRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageServiceImplement implements FileStorageService {

    private final String FOLDER_PATH = "C:\\Users\\AD\\Desktop\\Image";

    private final String RESOURCES_URL = "http://localhost:8080/api/v1/resources/";

    private final FileStorageRepository fileStorageRepository;

    @Autowired
    public FileStorageServiceImplement(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    public URI uploadFile(MultipartFile file) throws IOException {
        FileStorage fileStorage = fileStorageRepository.save(
          FileStorage
                  .builder()
                  .name(file.getOriginalFilename())
                  .contentType(file.getContentType())
                  .size(file.getSize())
                  .build()
        );
        file.transferTo(
                getFilePath(
                        fileStorage.getUuid(),
                        file.getOriginalFilename()
                )
        );
        return URI.create(
                RESOURCES_URL.concat(
                    fileStorage.getUuid().toString()
                )
        );
    }

    @Override
    public void removeFile(UUID uuid) throws IOException {
        FileStorage fileStorage = fileStorageRepository
                .findById(uuid)
                .orElseThrow();
        Files.deleteIfExists(
                getFilePath(
                        fileStorage.getUuid(),
                        fileStorage.getName()
                )
        );
        fileStorageRepository.deleteById(uuid);
    }

    @Override
    public Resource getFile(UUID uuid) throws IOException {
        FileStorage fileStorage = fileStorageRepository
                .findById(uuid)
                .orElseThrow();
        return new ByteArrayResource(
                Files.readAllBytes(
                    getFilePath(fileStorage.getUuid(), fileStorage.getName())
                ),
                fileStorage.getContentType()
        );
    }

    public Path getFilePath(UUID uuid, String originalFilename){
        String fileName = uuid.toString()
                .concat(
                        getExtensionFromFileName(originalFilename)
                );
        return Path.of(
                FOLDER_PATH.concat("\\").concat(fileName)
        );
    }

    private String getExtensionFromFileName(String name){
        return name.substring(
                name.lastIndexOf(".")
        );
    }
}
