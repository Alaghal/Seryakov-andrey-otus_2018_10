package ru.otus.L14.repository;

import ru.otus.L14.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    long create(String name);
}
