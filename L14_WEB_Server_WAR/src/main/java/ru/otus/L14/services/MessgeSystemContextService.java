package ru.otus.L14.services;


import ru.otus.L15.messageSystem.MessageSystem;
import ru.otus.L15.messageSystem.MessageSystemContext;
import ru.otus.L15.messageSystem.entity.Address;

public interface MessgeSystemContextService {
     MessageSystemContext getMessageSystemContext();
     MessageSystem getMessageSystem();
     Address getFrontAddress();
     void setFrontAddress(Address address);
     Address getDbAddress();
     void setDbAddress(Address address);
}
