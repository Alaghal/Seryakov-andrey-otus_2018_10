package ru.otus.L14.services;

import org.springframework.stereotype.Service;


@Service
public class UserEntityProviderService implements EntityPathProviderService {
    private final String PATH_FOR_PACKAGE_ENTITY="ru.otus.l10.orm.users";

    @Override
    public String getEntityPackagePath() {
        return PATH_FOR_PACKAGE_ENTITY;
    }
}
