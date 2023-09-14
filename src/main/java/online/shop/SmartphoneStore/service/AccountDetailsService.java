package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.repository.AccountRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
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

}
