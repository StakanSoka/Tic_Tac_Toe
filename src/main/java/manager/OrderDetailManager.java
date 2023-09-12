package manager;

import bean.Item;
import bean.OrderDetail;
import bean.UserOrder;
import dao.OrderDetailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class OrderDetailManager {

    private OrderDetailDAO orderDetailDAO;

    public List<OrderDetail> findByUserOrder(int userOrderId) {
        return orderDetailDAO.findByUserOrder(userOrderId);
    }

    public void save(OrderDetail orderDetail) {
        orderDetailDAO.save(orderDetail);
    }

    public void delete(OrderDetail orderDetail) {
        orderDetailDAO.delete(orderDetail);
    }

    public OrderDetail create(int tableId, Item item, UserOrder userOrder) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setItem(item);
        orderDetail.setTableId(tableId);
        orderDetail.setUserOrder(userOrder);


        return orderDetail;
    }

    public OrderDetail find(int tableId, int itemId, int userOrderId) {
        return orderDetailDAO.find(tableId, itemId, userOrderId);
    }

    @Autowired
    public void setOrderDetailDAO(OrderDetailDAO orderDetailDAO) {
        this.orderDetailDAO = orderDetailDAO;
    }
}