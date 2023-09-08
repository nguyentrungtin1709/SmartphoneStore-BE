package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Address;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.repository.AddressRepository;
import online.shop.SmartphoneStore.service.Interface.AddressService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImplement implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImplement(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> readAllAddressOfAccount(Account account) {
        return addressRepository.findAllByAccount_Id(account.getId());
    }

    @Override
    public Address readAddressByAccountAndId(Account account, Long addressId) throws DataNotFoundException {
        return addressRepository
                .findAddressByAccount_IdAndId(
                        account.getId(),
                        addressId
                )
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy địa chỉ"));
    }

    @Override
    public Address updateAddress(Account account, Long addressId, Address newAddress) throws DataNotFoundException {
        Address address = addressRepository
                .findAddressByAccount_IdAndId(
                        account.getId(),
                        addressId
                )
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy địa chỉ"));
        if (isUpdate(address.getCity(), newAddress.getCity())){
            address.setCity(newAddress.getCity());
        }

        if (isUpdate(address.getDistrict(), newAddress.getDistrict())){
            address.setDistrict(newAddress.getDistrict());
        }

        if (isUpdate(address.getCommune(), newAddress.getCommune())){
            address.setCommune(newAddress.getCommune());
        }

        if (isUpdate(address.getAddressDetails(), newAddress.getAddressDetails())){
            address.setAddressDetails(newAddress.getAddressDetails());
        }
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Account account, Long addressId) {
        addressRepository.deleteByAccount_IdAndId(
                account.getId(),
                addressId
        );
    }

    private boolean isUpdate(String originValue, String newValue){
        return  Objects.nonNull(newValue) &&
                Strings.isNotBlank(newValue) &&
                !originValue.equals(newValue);
    }
}
