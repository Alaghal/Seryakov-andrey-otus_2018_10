package ru.otus.l12.servieces;

import org.springframework.stereotype.Service;

@Service
public class UserEntityProvider implements EntityPackagePathProvider {
    private final String PATH_FOR_PACKAGE_ENTITY="ru.otus.l10.orm.users";

    @Override
    public String getEntityPackagePath() {
        return PATH_FOR_PACKAGE_ENTITY;
    }
}
