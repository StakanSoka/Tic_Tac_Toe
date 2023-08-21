package dao;

import bean.LayoutPattern;
import bean.User;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class UserDAO extends AbstractDAO<User, Integer> {

    public User findByLogin(String login) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user;
        String sql = "SELECT * FROM " + getTableName() + " WHERE login = :login";

        session.beginTransaction();

        user = (User)session.createNativeQuery(sql, getEntityClass())
                .setParameter("login", login).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public String getTableName() {
        return "user";
    }
}