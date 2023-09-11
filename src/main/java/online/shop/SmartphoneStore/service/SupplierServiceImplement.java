package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Supplier;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.repository.SupplierRepository;
import online.shop.SmartphoneStore.service.Interface.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImplement implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImplement(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) throws UniqueConstraintException {
        boolean hasSupplier = supplierRepository
                .findSupplierByName(supplier.getName())
                .isPresent();
        if (hasSupplier){
            throw new UniqueConstraintException(Map.of("name", "Nhà cung cấp đã tồn tại"));
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> readAllSuppliers() {
        return supplierRepository.findAll(Sort.by("name"));
    }

    @Override
    public Supplier readSupplierById(Integer supplierId) throws DataNotFoundException {
        return null;
    }

    @Override
    public Supplier updateSupplier(Integer supplierId, Supplier supplier) throws UniqueConstraintException {
        return null;
    }

    @Override
    public void deleteSupplierById(Integer supplierId) {

    }
}
