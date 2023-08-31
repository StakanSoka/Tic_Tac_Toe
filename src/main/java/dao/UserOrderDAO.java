package dao;

import bean.OrderStatus;
import bean.UserOrder;
import config.HibernateFactoryConfig;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserOrderDAO extends AbstractDAO<UserOrder, Integer> {

    public List<UserOrder> findAllUserOrdersWithStatus(int userId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserOrder> userOrders;
        OrderStatus orderStatus;
        String query = "select * from user_order where user_id = :userId";

        session.beginTransaction();

        try {
            userOrders = session.createNativeQuery(query, getEntityClass())
                    .setParameter("userId", userId).stream().toList();
        } catch (NoResultException e) {
            return null;
        }

        for (UserOrder userOrder : userOrders) {
            query = "select * from order_status where id = :statusId";
            orderStatus = session.createNativeQuery(query, OrderStatus.class)
                    .setParameter("statusId", userOrder.getOrderStatus().getId()).getSingleResult();
            userOrder.setOrderStatus(orderStatus);
        }

        session.getTransaction().commit();
        session.close();

        return userOrders;
    }

    @Override
    public Class<UserOrder> getEntityClass() {
        return UserOrder.class;
    }

    @Override
    public String getTableName() {
        return "user_order";
    }
}