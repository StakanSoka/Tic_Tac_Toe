package dao;

import bean.UserSymbolMap;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSymbolMapDAO extends AbstractDAO<UserSymbolMap, Integer> {

    public List<UserSymbolMap> findByUserId(int userId) {
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

    @Override
    public Class<UserSymbolMap> getEntityClass() {
        return UserSymbolMap.class;
    }

    @Override
    public String getTableName() {
        return "user_symbol_map";
    }
}
