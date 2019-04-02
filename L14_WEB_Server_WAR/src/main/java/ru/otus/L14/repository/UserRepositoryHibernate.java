package ru.otus.L14.repository;

import org.springframework.stereotype.Repository;
import ru.otus.L14.services.EntityPathProviderService;
import ru.otus.l11.hibernate.HibernateRepository;


@Repository
public class UserRepositoryHibernate extends HibernateRepository {

    public UserRepositoryHibernate (EntityPathProviderService packageProviderOfEntity) {
        super( packageProviderOfEntity.getEntityPackagePath() );
    }


}

