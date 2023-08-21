package dao;

import bean.LayoutPattern;
import bean.UserLayoutPatternMap;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLayoutPatternMapDAO extends AbstractDAO<UserLayoutPatternMap, Integer> {

    public List<UserLayoutPatternMap> findAllByUserId(int userId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserLayoutPatternMap> layoutPatterns;
        String request = "SELECT * FROM user_layout_pattern_map WHERE user_id = :userId";

        session.beginTransaction();

        layoutPatterns = session.createNativeQuery(request, getEntityClass())
                .setParameter("userId", userId)
                .stream().toList();

        session.getTransaction().commit();
        session.close();

        return layoutPatterns;

    }

    @Override
    public Class<UserLayoutPatternMap> getEntityClass() {
        return UserLayoutPatternMap.class;
    }

    @Override
    public String getTableName() {
        return "user_layout_pattern_map";
    }
}
