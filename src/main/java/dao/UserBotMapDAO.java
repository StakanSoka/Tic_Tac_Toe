package dao;

import bean.UserBotMap;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBotMapDAO extends AbstractDAO<UserBotMap, Integer> {

    public List<UserBotMap> findByUserId(int userId) {
        List<UserBotMap> userBotMaps;
        String request = "SELECT * FROM user_bot_map WHERE user_id = :userId";
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        userBotMaps = session.createNativeQuery(request, getEntityClass())
                .setParameter("userId", userId)
                .stream().toList();

        session.getTransaction().commit();
        session.close();

        return userBotMaps;
    }

    @Override
    public Class<UserBotMap> getEntityClass() {
        return UserBotMap.class;
    }

    @Override
    public String getTableName() {
        return "user_bot_map";
    }
}
