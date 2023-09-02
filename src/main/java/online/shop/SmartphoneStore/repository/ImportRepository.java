package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {

}
