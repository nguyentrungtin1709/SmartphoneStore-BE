package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Address;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;

import java.util.List;

public interface AddressService {
    Address saveAddress(Address address);

    List<Address> readAllAddressOfAccount(Account account);

    Address readAddressByAccountAndId(Account account, Long addressId) throws DataNotFoundException;

    Address updateAddress(Account account, Long addressId, Address address) throws DataNotFoundException;

    void deleteAddress(Account account, Long addressId);
}
