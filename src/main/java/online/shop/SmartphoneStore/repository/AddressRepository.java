package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByAccount_Id(Long accountId);

    Optional<Address> findAddressByAccount_IdAndId(Long accountId, Long addressId);

    Integer countAddressByAccount_Id(Long accountId);

    void deleteByAccount_IdAndId(Long accountId, Long addressId);

}
