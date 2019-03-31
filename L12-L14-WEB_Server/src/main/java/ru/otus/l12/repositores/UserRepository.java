package ru.otus.l12.repositores;

import org.springframework.stereotype.Repository;

import ru.otus.l11.hibernate.HibernateRepository;
import ru.otus.l12.servieces.EntityPackagePathProvider;

@Repository
public class UserRepository extends HibernateRepository {

    public UserRepository(EntityPackagePathProvider packageProviderOfEntity) {
        super( packageProviderOfEntity.getEntityPackagePath() );
    }
}
