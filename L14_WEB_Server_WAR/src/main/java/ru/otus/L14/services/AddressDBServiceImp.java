package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.l11.entity.Address;

@Service
public class AddressDBServiceImp implements AddressDBService {
     private final Address address = new Address( "Hibernate" );

    @Override
    public Address getAddressDbForUser(){
        return address;
    }
}
