package manager;

import bean.LayoutPattern;
import dao.LayoutPatternDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class LayoutPatternManager {

    private LayoutPatternDAO layoutPatternDAO;

    public LayoutPattern find(int id) {
        return layoutPatternDAO.find(id);
    }

    @Autowired
    public void setLayoutPatternDAO(LayoutPatternDAO layoutPatternDAO) {
        this.layoutPatternDAO = layoutPatternDAO;
    }
}
