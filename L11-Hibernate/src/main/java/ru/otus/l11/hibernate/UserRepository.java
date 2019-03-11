package ru.otus.l11.hibernate;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.l10.orm.users.AddressDataSet;
import ru.otus.l10.orm.users.PhoneDataSet;
import ru.otus.l10.orm.users.User;

import java.util.List;

public class UserRepository implements Repository {
    private final SessionFactory sessionFactory;

    public UserRepository(){
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        List<Class<?>> classes = EntityScanner
                .scanPackages("ru.otus.l10.orm.users").result();

        MetadataSources metadata = new MetadataSources(serviceRegistry);
        for (Class<?> annotatedClass : classes) {
            metadata.addAnnotatedClass(annotatedClass);
        }

        sessionFactory = metadata.buildMetadata().buildSessionFactory();

    }
    @Override
    public <T>T load(long id, Class<T> clazz){
        T selected;
        try (Session session = sessionFactory.openSession()){
             selected = session.load(clazz, id);
           System.out.println( selected );
        }
        return selected;
    }
    @Override
    public <T> void save(T object ){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();

        }
    }

}
