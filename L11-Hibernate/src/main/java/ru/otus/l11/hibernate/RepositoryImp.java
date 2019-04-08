package ru.otus.l11.hibernate;

import ru.otus.l15.messageSystem.entity.Addressee;

import java.util.List;

public interface RepositoryImp<T> extends Addressee {

    public T load(long id, Class<T> clazz);
    public  void save(T object );
    public  void delete(T object);
    List<T> getAll(Class<T> clazz);
    <V>T getByValue(String nameField,V value,Class<T> clazz );
}
