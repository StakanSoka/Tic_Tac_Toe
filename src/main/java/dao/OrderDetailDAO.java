package dao;

import bean.OrderDetail;

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
