package dao;

import bean.OrderDetail;
import config.HibernateFactoryConfig;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDetailDAO extends AbstractDAO<OrderDetail, Integer> {

    public List<OrderDetail> findByUserOrder(int userOrderId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<OrderDetail> orderDetails;
        String query = "select * from order_detail where user_order_id = :userOrderId";

        session.beginTransaction();

        orderDetails = session.createNativeQuery(query, getEntityClass())
                .setParameter("userOrderId", userOrderId)
                .stream().toList();

        session.getTransaction().commit();
        session.close();

        return orderDetails;
    }

    public OrderDetail find(int tableId, int itemId, int userOrderId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        OrderDetail orderDetail;
        String query = "select * from order_detail where table_id = :tableId and item_id = :itemId and user_order_id = :userOrderId";

        session.beginTransaction();

        try {
            orderDetail = session.createNativeQuery(query, getEntityClass())
                    .setParameter("tableId", tableId)
                    .setParameter("itemId", itemId)
                    .setParameter("userOrderId", userOrderId)
                    .getSingleResult();

        } catch (NoResultException e) {
            orderDetail = null;
        }

        session.getTransaction().commit();
        session.close();

        return orderDetail;
    }

    @Override
    public Class<OrderDetail> getEntityClass() {
        return OrderDetail.class;
    }

    @Override
    public String getTableName() {
        return "order_detail";
    }
}
