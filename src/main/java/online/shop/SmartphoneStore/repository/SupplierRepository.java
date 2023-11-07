package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findSupplierByName(String name);

    Page<Supplier> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);

}
