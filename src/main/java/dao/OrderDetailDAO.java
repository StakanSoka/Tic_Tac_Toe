package dao;

import bean.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailDAO extends AbstractDAO<OrderDetail, Integer> {

    @Override
    public Class<OrderDetail> getEntityClass() {
        return OrderDetail.class;
    }

    @Override
    public String getTableName() {
        return "order_detail";
    }
}
