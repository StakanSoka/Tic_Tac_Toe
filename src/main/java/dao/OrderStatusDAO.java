package dao;

import bean.OrderStatus;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusDAO extends AbstractDAO<OrderStatus, Integer> {

    public OrderStatus findByStatus(String status) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        OrderStatus orderStatus;
        String query = "select * from order_status where name = :status";

        session.beginTransaction();

        orderStatus = session.createNativeQuery(query, getEntityClass())
                .setParameter("status", status)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();

        return orderStatus;
    }

    @Override
    public Class<OrderStatus> getEntityClass() {
        return OrderStatus.class;
    }

    @Override
    public String getTableName() {
        return "order_status";
    }
}