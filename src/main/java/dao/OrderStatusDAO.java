package dao;

import bean.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusDAO extends AbstractDAO<OrderStatus, Integer> {

    @Override
    public Class<OrderStatus> getEntityClass() {
        return OrderStatus.class;
    }

    @Override
    public String getTableName() {
        return "order_status";
    }
}
