package manager;

import bean.OrderStatus;
import dao.OrderStatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class OrderStatusManager {

    private OrderStatusDAO orderStatusDAO;

    public OrderStatus findByStatus(String status) {
        return orderStatusDAO.findByStatus(status);
    }

    @Autowired
    public void setOrderStatusDAO(OrderStatusDAO orderStatusDAO) {
        this.orderStatusDAO = orderStatusDAO;
    }
}
