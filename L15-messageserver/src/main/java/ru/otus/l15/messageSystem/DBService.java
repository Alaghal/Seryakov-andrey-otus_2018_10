package ru.otus.l15.messageSystem;

import ru.otus.l15.messageSystem.entity.Addressee;


/**
 * Created by tully.
 */
public interface DBService extends Addressee {
    void init();

    int getUserId(String name);
}
