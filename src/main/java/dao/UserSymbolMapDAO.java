package dao;

import bean.UserSymbolMap;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSymbolMapDAO extends AbstractDAO<UserSymbolMap, Integer> {

    public List<UserSymbolMap> findAllByUserId(int userId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserSymbolMap> userSymbolMaps;
        String request = "SELECT * FROM user_symbol_map WHERE user_id = :userId";

        session.beginTransaction();

        userSymbolMaps = session.createNativeQuery(request, getEntityClass())
                .setParameter("userId", userId)
                .stream().toList();

        session.getTransaction().commit();
        session.close();

        return userSymbolMaps;
    }

    public UserSymbolMap find(int userId, int symbolId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        UserSymbolMap userSymbolMap;
        String query = "select * from user_symbol_map where user_id = :userId and symbol_id = :symbolId";

        session.beginTransaction();

        userSymbolMap = session.createNativeQuery(query, getEntityClass())
                .setParameter("userId", userId)
                .setParameter("symbolId", symbolId)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();

        return userSymbolMap;
    }

    @Override
    public Class<UserSymbolMap> getEntityClass() {
        return UserSymbolMap.class;
    }

    @Override
    public String getTableName() {
        return "user_symbol_map";
    }
}
