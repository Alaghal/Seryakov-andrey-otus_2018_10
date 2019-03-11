package ru.otus.l11.hibernate;

public class FactoryUserRepository implements FactoryRepositories {
    @Override
    public Repository creatRepository() {
        return new UserRepository();
    }
}
