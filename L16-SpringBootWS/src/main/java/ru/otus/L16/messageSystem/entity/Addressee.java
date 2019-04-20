package ru.otus.L16.messageSystem.entity;


import ru.otus.L16.messageSystem.MessageSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
