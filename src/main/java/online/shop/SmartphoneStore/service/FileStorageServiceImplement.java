package online.shop.SmartphoneStore.service;

import jakarta.annotation.PostConstruct;
import online.shop.SmartphoneStore.entity.FileStorage;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.repository.FileStorageRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageServiceImplement implements FileStorageService {

    private final String FOLDER_PATH = Path.of("./Image").toAbsolutePath().normalize().toString();

    private final FileStorageRepository fileStorageRepository;

    @Autowired
    public FileStorageServiceImplement(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        Path folerPath = Path.of(FOLDER_PATH);
        boolean exist =  Files.exists(folerPath);
        if (!exist){
            Files.createDirectory(folerPath);
        }
    }

    @Override
    @Transactional(rollbackFor = {NoSuchFileException.class})
    public URI uploadFile(MultipartFile file, String RESOURCES_URL) throws IOException {
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
    public Resource getFile(UUID uuid) throws IOException, DataNotFoundException {
        FileStorage fileStorage = fileStorageRepository
                .findById(uuid)
                .orElseThrow(() -> new DataNotFoundException("File không tồn tại trong hệ thống"));
        Path filePath = getFilePath(fileStorage.getUuid(), fileStorage.getName());
        if (!Files.exists(filePath)){
            throw new DataNotFoundException("File không tồn tại trong hệ thống");
        }
        return new ByteArrayResource(
                Files.readAllBytes(
                    filePath
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
