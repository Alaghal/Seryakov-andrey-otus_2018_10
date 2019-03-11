package ru.otus.l11.hibernate;

public interface Repository {

    public <T>T load(long id, Class<T> clazz);
    public <T> void save(T object );
}
