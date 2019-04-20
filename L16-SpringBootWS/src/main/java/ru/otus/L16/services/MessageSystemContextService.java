package ru.otus.L16.services;


import ru.otus.L16.messageSystem.MessageSystem;
import ru.otus.L16.messageSystem.MessageSystemContext;
import ru.otus.L16.messageSystem.entity.Address;

public interface MessageSystemContextService {
     MessageSystemContext getMessageSystemContext();
     MessageSystem getMessageSystem();
     Address getFrontAddress();
     void setFrontAddress(Address address);
     Address getDbAddress();
     void setDbAddress(Address address);
}
