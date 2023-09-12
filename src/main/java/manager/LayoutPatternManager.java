package manager;

import bean.LayoutPattern;
import bean.OrderDetail;
import dao.LayoutPatternDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import util.Constants;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class LayoutPatternManager {

    private LayoutPatternDAO layoutPatternDAO;

    public LayoutPattern createLayoutPattern(String position1, String position2, int price) {
        LayoutPattern layoutPattern = new LayoutPattern();

        layoutPattern.setPosition1(position1);
        layoutPattern.setPosition2(position2);
        layoutPattern.setPrice(price);

        return layoutPattern;
    }

    public List<LayoutPattern> findByOrderDetails(List<OrderDetail> orderDetails) {
        List<LayoutPattern> layoutPatterns;
        int layoutPatternsSize = 0;

        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getTableId() == Constants.OrderDetailTables.LAYOUT_PATTERN) {
                layoutPatternsSize++;
            }
        }

        layoutPatterns = new ArrayList<>(layoutPatternsSize);

        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getTableId() == Constants.OrderDetailTables.LAYOUT_PATTERN) {
                layoutPatterns.add(layoutPatternDAO.find(orderDetail.getItem().getId()));
            }
        }

        return layoutPatterns;
    }

    public void save(LayoutPattern layoutPattern) {
        layoutPatternDAO.save(layoutPattern);
    }

    public void update(LayoutPattern layoutPattern) {
        layoutPatternDAO.update(layoutPattern);
    }

    public LayoutPattern find(int id) {
        return layoutPatternDAO.find(id);
    }

    public List<LayoutPattern> findAll() {
        return layoutPatternDAO.findAll();
    }

    public void delete(LayoutPattern layoutPattern) {
        layoutPatternDAO.delete(layoutPattern);
    }

    @Autowired
    public void setLayoutPatternDAO(LayoutPatternDAO layoutPatternDAO) {
        this.layoutPatternDAO = layoutPatternDAO;
    }
}