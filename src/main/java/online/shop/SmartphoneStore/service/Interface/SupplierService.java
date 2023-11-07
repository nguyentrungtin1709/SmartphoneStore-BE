package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Supplier;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService {
    Supplier saveSupplier(Supplier supplier) throws UniqueConstraintException;

    Page<Supplier> readAllSuppliers(Integer page);

    Supplier readSupplierById(Integer supplierId) throws DataNotFoundException;

    Supplier updateSupplier(Integer supplierId, Supplier supplier) throws UniqueConstraintException, DataNotFoundException;

    void deleteSupplierById(Integer supplierId) throws DataNotFoundException;
}
