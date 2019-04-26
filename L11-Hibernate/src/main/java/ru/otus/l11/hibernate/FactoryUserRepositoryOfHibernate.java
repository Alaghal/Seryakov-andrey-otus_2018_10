package ru.otus.l11.hibernate;

public class FactoryUserRepositoryOfHibernate implements FactoryRepositories {
    @Override
    public RepositoryImp createRepository() {
        return new HibernateRepository("ru.otus.l11.hibernate.entity");
    }
}
