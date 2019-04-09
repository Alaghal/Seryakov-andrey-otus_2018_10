package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.l11.entity.Address;

@Service
public class AddressFrontendServiceImp implements AddressFrontendService{
    private final Address address = new Address( "Frontend" );

    @Override
    public Address getFrontendAddress() {
        return address;
    }
}
