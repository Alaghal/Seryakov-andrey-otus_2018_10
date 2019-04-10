package ru.otus.l11.hibernate;
import java.util.List;

public interface RepositoryImp<T>  {

    public T load(long id, Class<T> clazz);
    public  void save(T object );
    public  void delete(T object);
    List<T> getAll(Class<T> clazz);
    <V>T getByValue(String nameField,V value,Class<T> clazz );
}
