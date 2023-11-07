package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.payload.ProfileChanging;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.repository.AccountRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final FileStorageService fileStorageService;

    @Autowired
    public AccountDetailsService(
            AccountRepository accountRepository,
            FileStorageService fileStorageService
    ) {
        this.accountRepository = accountRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
                .findAccountByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Account updateAvatar(String email, MultipartFile file) throws IOException {
        Account account = accountRepository
                .findAccountByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));
        if (account.getImageUrl() != null){
            String path = account.getImageUrl().getPath();
            UUID uuid = UUID.fromString(
                    path.substring(
                            path.lastIndexOf("/") + 1
                    )
            );
            fileStorageService.removeFile(uuid);
        }
        URI imageUrl = fileStorageService.uploadFile(file);
        account.setImageUrl(imageUrl);
        return accountRepository.save(account);
    }

    public boolean wasRegisteredEmail(String email){
        return accountRepository.existsAccountByEmail(email);
    }

    public boolean wasRegisteredPhone(String phone){
        return accountRepository.existsAccountByPhone(phone);
    }

    public Account updatePhone(Long id, String phone) throws UniqueConstraintException {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));
        if (wasRegisteredPhone(phone) && !phone.equals(account.getPhone())){
            throw new UniqueConstraintException(Map.of("phone", "Số điện thoại đã tồn tại"));
        }
        account.setPhone(phone);
        return accountRepository.save(account);
    }

    public Account updateProfile(Long id, ProfileChanging profile) throws DataNotFoundException {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy tài khoản"));
        if (!account.getName().equals(profile.getName())){
            account.setName(profile.getName());
        }
        account.setBirthday(profile.getBirthday());
        account.setGender(profile.getGender());
        return accountRepository.save(account);
    }

    public Page<Account> readAllAccounts(Integer page) {
        return accountRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 12));
    }

    public Account readAccountById(Long accountId) throws DataNotFoundException {
        return accountRepository
                .findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy tài khoản"));
    }

    public void deleteAccountById(Long accountId) throws DataNotFoundException {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy tài khoản"));
        accountRepository.deleteById(accountId);
    }

    public Page<Account> searchAccountByEmail(String keyword, Integer page) {
        return accountRepository.searchAccountsByEmail(keyword, PageRequest.of(page, 12));
    }

    public Map<String, Long> countAllAccounts() {
        Long count = accountRepository.count();
        return Map.of("numberOfAccounts", count);
    }

    public Map<String, Long> countAccountsCreatedToday() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 0, 0, 0, 0);
        Long count = accountRepository.countAccountsByCreatedAtBetween(start, end);
        return Map.of("numberOfAccountsToday", count);
    }
}
