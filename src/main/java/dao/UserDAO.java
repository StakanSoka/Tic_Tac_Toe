package dao;

import bean.User;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO extends AbstractDAO<User, Integer> {

    public User find(String login) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user;
        String sql = "SELECT * FROM :tableName WHERE login = :login";


        session.beginTransaction();

        user = (User)session.createNativeQuery(sql, entityClass)
                .setParameter("tableName", tableName)
                .setParameter("login", login).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return user;
    }
}
