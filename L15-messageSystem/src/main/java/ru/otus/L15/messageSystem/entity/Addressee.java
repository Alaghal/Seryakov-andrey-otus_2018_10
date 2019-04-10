package ru.otus.L15.messageSystem.entity;

import ru.otus.L15.messageSystem.MessageSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
