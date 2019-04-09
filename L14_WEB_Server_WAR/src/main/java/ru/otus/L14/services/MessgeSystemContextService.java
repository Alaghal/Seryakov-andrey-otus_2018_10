package ru.otus.L14.services;

import ru.otus.l11.entity.Address;
import ru.otus.l15.messageSystem.MessageSystemContext;
import ru.otus.l15.messageSystem.entity.MessageSystem;

public interface MessgeSystemContextService {
    public MessageSystemContext getMessageSystemContext();
    public MessageSystem getMessageSystem();
    public Address getFrontAddress();
    public void setFrontAddress(Address address);
    public Address getDbAddress();
    public void setDbAddress(Address address);
}
