package dao;

import bean.UserOrder;

public class UserOrderDAO extends AbstractDAO<UserOrder, Integer> {

    @Override
    public Class<UserOrder> getEntityClass() {
        return UserOrder.class;
    }

    @Override
    public String getTableName() {
        return "user_order";
    }
}
