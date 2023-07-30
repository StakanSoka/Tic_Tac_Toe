package dao;

import manager.HibernateFactoryManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractDAO<T, ID> {

    public void save(T entity) {
        HibernateFactoryManager factoryManager = HibernateFactoryManager.getInstance();
        SessionFactory sessionFactory = factoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.persist(entity);

        session.getTransaction().commit();
    }

    public void update(T entity) {
        HibernateFactoryManager factoryManager = HibernateFactoryManager.getInstance();
        SessionFactory sessionFactory = factoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.merge(entity);

        session.getTransaction().commit();
    }

    public void delete(T entity) {
        HibernateFactoryManager factoryManager = HibernateFactoryManager.getInstance();
        SessionFactory sessionFactory = factoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.remove(entity);

        session.getTransaction().commit();
    }

    public T getById(ID id) {
        HibernateFactoryManager factoryManager = HibernateFactoryManager.getInstance();
        SessionFactory sessionFactory = factoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        T entity = session.get(getEntityClass(), id);

        session.getTransaction().commit();

        return entity;
    }

    protected abstract Class<T> getEntityClass();

}
