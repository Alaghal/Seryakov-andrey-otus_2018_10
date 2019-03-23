package ru.otus.l11.hibernate;

public class FactoryUserRepositoryOfHibernate implements FactoryRepositories {
    @Override
    public Repository createRepository() {
        return new HibernateRepository("ru.otus.l10.orm.users");
    }
}
