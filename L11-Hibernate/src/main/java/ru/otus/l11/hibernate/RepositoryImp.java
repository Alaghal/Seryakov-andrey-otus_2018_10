package ru.otus.l11.hibernate;

import java.util.List;

public interface RepositoryImp<T> {

    T load(long id, Class<T> clazz);
    void save(T object);
    void delete(T object);
    List<T> getAll(Class<T> clazz);
    <V>T getByValue(String nameField,V value,Class<T> clazz );
}
