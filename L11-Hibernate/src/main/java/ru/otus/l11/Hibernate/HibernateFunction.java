package ru.otus.l11.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.l10.orm.users.AddressDataSet;
import ru.otus.l10.orm.users.PhoneDataSet;
import ru.otus.l10.orm.users.SimpleUser;
import ru.otus.l10.orm.users.User;

public class HibernateFunction {
    private final SessionFactory sessionFactory;

    public  HibernateFunction(){
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass( User.class)
                .addAnnotatedClass( AddressDataSet.class)
                .addAnnotatedClass( PhoneDataSet.class )
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();

    }

    public <T>T load(long id, Class<T> clazz){
        T selected;
        try (Session session = sessionFactory.openSession()){
             selected = session.load(clazz, id);
           System.out.println( selected );
        }
        return selected;
    }

    public void save(Object object ){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();

        }
    }

}
