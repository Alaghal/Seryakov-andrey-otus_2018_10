package ru.otus.l11.entity;

import ru.otus.l11.entity.Address;
import ru.otus.l15.messageSystem.entity.MessageSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
