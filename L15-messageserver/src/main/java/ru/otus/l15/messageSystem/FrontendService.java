package ru.otus.l15.messageSystem;


import ru.otus.l15.messageSystem.entity.Addressee;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {
    void init();

    void handleRequest(String login);

    void addUser(int id, String name);
}

