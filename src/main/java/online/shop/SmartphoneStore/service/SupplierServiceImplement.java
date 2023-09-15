package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Supplier;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.repository.SupplierRepository;
import online.shop.SmartphoneStore.service.Interface.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new DataNotFoundException("Nhà cung cấp không tồn tại"));
    }

    @Override
    public Supplier updateSupplier(Integer supplierId, Supplier update)
            throws UniqueConstraintException, DataNotFoundException
    {
        Supplier supplier = supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new DataNotFoundException("Nhà cung cấp không tồn tại"));
        if (!supplier.getName().equals(update.getName())){
            boolean hasSupplier = supplierRepository
                    .findSupplierByName(update.getName())
                    .isPresent();
            if (hasSupplier){
                throw new UniqueConstraintException(Map.of("name", "Nhà cung cấp đã tồn tại"));
            }
            supplier.setName(update.getName());
        }
        if (!supplier.getEmail().equals(update.getEmail())){
            supplier.setEmail(update.getEmail());
        }
        if (!supplier.getPhone().equals(update.getPhone())){
            supplier.setPhone(update.getPhone());
        }
        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public void deleteSupplierById(Integer supplierId) throws DataNotFoundException {
        supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new DataNotFoundException("Nhà cung cấp không tồn tại"));
        supplierRepository.deleteById(supplierId);
    }
}
