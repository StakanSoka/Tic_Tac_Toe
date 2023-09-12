package manager;

import bean.OrderStatus;
import bean.User;
import bean.UserOrder;
import dao.UserOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Component
@Service
public class UserOrderManager {

    private UserOrderDAO userOrderDAO;

    public void saveUserOrder(UserOrder userOrder) {
        userOrderDAO.save(userOrder);
    }

    public void updateUserOrder(UserOrder userOrder) {
        userOrderDAO.update(userOrder);
    }

    public void deleteUserOrder(UserOrder userOrder) {
        userOrderDAO.delete(userOrder);
    }

    public UserOrder findById(int userOrderId) {
        return userOrderDAO.find(userOrderId);
    }

    public List<UserOrder> findAll() {
        return userOrderDAO.findAll();
    }

    public UserOrder create(OrderStatus orderStatus, User user) {
        UserOrder userOrder = new UserOrder();

        userOrder.setCreatedDate(LocalDate.now());
        userOrder.setModifiedDate(LocalDate.now());
        userOrder.setOrderStatus(orderStatus);
        userOrder.setUser(user);

        return userOrder;
    }

    public List<UserOrder> findAllUserOrdersWithOrderStatus(int userId) {
        return userOrderDAO.findAllUserOrdersWithStatus(userId);
    }

    public UserOrder findActiveUserOrder(int userId) {
        List<UserOrder> userOrders = userOrderDAO.findAllUserOrdersWithStatus(userId);

        if (userOrders == null) return null;

        for (UserOrder userOrder : userOrders) {
            if (userOrder.getOrderStatus().getName().equals("ACTIVE")) {
                return userOrder;
            }
        }

        return null;
    }

    @Autowired
    public void setUserOrderDAO(UserOrderDAO userOrderDAO) {
        this.userOrderDAO = userOrderDAO;
    }
}
