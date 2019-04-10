package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.L15.messageSystem.entity.Address;


@Service
public class AddressFrontendServiceImp implements AddressFrontendService{
    private final Address address = new Address( "Frontend" );

    @Override
    public Address getFrontendAddress() {
        return address;
    }
}
