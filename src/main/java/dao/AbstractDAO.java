package dao;

import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDAO<T, ID> {

    protected Class<T> entityClass;
    protected String tableName;

    public void save(T entity) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.persist(entity);

        session.getTransaction().commit();
        session.close();
    }

    public void update(T entity) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.merge(entity);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(T entity) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.remove(entity);

        session.getTransaction().commit();
        session.close();
    }

    public T find(ID id) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        T entity = session.get(entityClass, id);

        session.getTransaction().commit();
        session.close();

        return entity;
    }

    public Set<T> findAll() {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String selectAll = "SELECT * FROM " + tableName;

        session.beginTransaction();

        Set<T> entities = session.createNativeQuery(selectAll, entityClass).getResultStream().
                collect(Collectors.toSet());

        session.getTransaction().commit();
        session.close();

        return entities;
    }
}