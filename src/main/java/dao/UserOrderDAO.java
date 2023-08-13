package dao;

import bean.UserOrder;
import org.springframework.stereotype.Component;

@Component
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
