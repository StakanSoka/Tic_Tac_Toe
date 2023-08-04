package dao;

import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public abstract class AbstractDAO<T, ID> {

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

        T entity = session.get(getEntityClass(), id);

        session.getTransaction().commit();
        session.close();

        return entity;
    }

    public List<T> findAll() {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String selectAll = "SELECT * FROM " + getTableName();

        session.beginTransaction();

        List<T> entities = session.createNativeQuery(selectAll, getEntityClass()).getResultStream().toList();

        session.getTransaction().commit();
        session.close();

        return entities;
    }

    public abstract Class<T> getEntityClass();

    public abstract String getTableName();
}