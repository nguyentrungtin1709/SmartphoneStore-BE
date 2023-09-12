package online.shop.SmartphoneStore.service.Interface;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SmartphoneService {

    Smartphone saveSmartphone(Smartphone smartphone, MultipartFile file)
            throws UniqueConstraintException, IOException;

    Page<Smartphone> readSmartphones(Integer page);
}
