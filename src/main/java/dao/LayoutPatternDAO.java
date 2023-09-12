package dao;

import bean.LayoutPattern;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class LayoutPatternDAO extends AbstractDAO<LayoutPattern, Integer> {

    @Override
    public void save(LayoutPattern layoutPattern) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String sqlQuery = "INSERT INTO layout_pattern(position1, position2, price) VALUES (:position1, :position2, :price)";

        session.beginTransaction();

        session.createNativeQuery(sqlQuery, getEntityClass())
                .setParameter("position1", layoutPattern.getPosition1())
                .setParameter("position2", layoutPattern.getPosition2())
                .setParameter("price", layoutPattern.getPrice())
                .executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Class<LayoutPattern> getEntityClass() {
        return LayoutPattern.class;
    }

    @Override
    public String getTableName() {
        return "layout_pattern";
    }
}
