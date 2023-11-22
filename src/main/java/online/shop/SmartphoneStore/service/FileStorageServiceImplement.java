package online.shop.SmartphoneStore.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import online.shop.SmartphoneStore.entity.FileStorage;
import online.shop.SmartphoneStore.repository.FileStorageRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.UUID;

@Service
public class FileStorageServiceImplement implements FileStorageService {

    private final AmazonS3 s3Client;

    private final FileStorageRepository fileStorageRepository;

    @Value("${cloud.s3.bucket.name}")
    private String bucketName;

    @Autowired
    public FileStorageServiceImplement(AmazonS3 s3Client, FileStorageRepository fileStorageRepository) {
        this.s3Client = s3Client;
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    @Transactional(rollbackFor = {NoSuchFileException.class})
    public URI uploadFile(MultipartFile file) throws URISyntaxException {
        FileStorage fileStorage = fileStorageRepository.save(
          FileStorage
                  .builder()
                  .name(file.getOriginalFilename())
                  .contentType(file.getContentType())
                  .size(file.getSize())
                  .build()
        );
        File object = convertMultiPartFileToFile(file);
        String fileName = getFileName(fileStorage.getUuid(), fileStorage.getName());
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, object));
        boolean isDeleted = object.delete();
        return s3Client.getUrl(bucketName, fileName).toURI();
    }

    @Override
    public void removeFile(String fileName) throws URISyntaxException {
        s3Client.deleteObject(bucketName, fileName);
        UUID uuid = UUID.fromString(fileName.substring(0, fileName.lastIndexOf(".")));
        fileStorageRepository.deleteById(uuid);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return convertedFile;
    }

    public String getFileName(UUID uuid, String originalFilename){
        return uuid.toString()
                .concat(
                        getExtensionFromFileName(originalFilename)
                );
    }

    private String getExtensionFromFileName(String name){
        return name.substring(
                name.lastIndexOf(".")
        );
    }
}
