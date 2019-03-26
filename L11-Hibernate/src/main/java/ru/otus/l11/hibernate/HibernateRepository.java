package ru.otus.l11.hibernate;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.otus.l10.orm.users.MyUser;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateRepository<T> implements Repository<T> {
    private final SessionFactory sessionFactory;
    StandardServiceRegistry serviceRegistry = null;

    public HibernateRepository(String packageOfEntity) {
        Configuration configuration = new Configuration()
                .configure( "hibernate.cfg.xml" );
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings( configuration.getProperties() ).build();

        List<Class<?>> classes = EntityScanner
                .scanPackages(packageOfEntity ).result();

        MetadataSources metadata = new MetadataSources( serviceRegistry );
        for (Class<?> annotatedClass : classes) {
            metadata.addAnnotatedClass( annotatedClass );
        }

        sessionFactory = metadata.buildMetadata().buildSessionFactory();
    }


    @Override
    public T load(long id, Class<T> clazz) {
        T selected;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            selected = session.load( clazz, id );
            session.getTransaction().commit();
            Hibernate.initialize( selected );
            return selected;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    @Override
    public void save(T object) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save( object );
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(T object) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete( object );
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<T> getAll(Class<T> clazz) {
        Transaction transaction = null;
        String  sql= "from " +clazz.getSimpleName();
        System.out.println("sql = " + sql);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<T> listValues = session.createQuery(sql).list();
/*            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery( clazz );
            Root<T> root = query.from( clazz );
            query.select( root );
            Query<T> q = session.createQuery( query );
            List<T> listValues = q.getResultList();*/

            Hibernate.initialize( listValues );
            return listValues;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }


    @Override
    public <V> T getByValue(String nameField, V inputValue, Class<T> clazz) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery( clazz );
            Root<T> root = query.from( clazz );
            query.select( root ).where( builder.equal( root.get( nameField ), inputValue ) );
            Query<T> q = session.createQuery( query );
            T value;
            try {
                 value = q.getSingleResult();
                Hibernate.initialize( value );
            }catch (NoResultException e){
                System.out.println( "Not result" );
                return null;
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }


}
