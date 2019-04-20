package ru.otus.L16.services;


import org.springframework.stereotype.Service;
import ru.otus.L16.messageSystem.entity.Address;

@Service
public class AddressDBServiceImp implements AddressDBService {
     private final Address address = new Address( "Hibernate" );

    @Override
    public Address getAddressDbForUser(){
        return address;
    }
}
