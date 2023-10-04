package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByEmail(String email);

    boolean existsAccountByEmail(String email);

    boolean existsAccountByPhone(String phone);

    @Query(
            value = "SELECT * FROM tai_khoan WHERE email LIKE %:keyword%",
            nativeQuery = true
    )
    Page<Account> searchAccountsByEmail(String keyword, PageRequest page);

    Page<Account> findAllByOrderByName(PageRequest page);

}
