package manager;

import bean.LayoutPattern;
import bean.User;
import bean.UserLayoutPatternMap;
import dao.UserLayoutPatternMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
@Service
public class UserLayoutPatternMapManager {

    private UserLayoutPatternMapDAO userLayoutPatternMapDAO;

    public void save(UserLayoutPatternMap userLayoutPatternMap) {
        userLayoutPatternMapDAO.save(userLayoutPatternMap);
    }

    public UserLayoutPatternMap create(User user, LayoutPattern layoutPattern) {
        UserLayoutPatternMap userLayoutPatternMap = new UserLayoutPatternMap();

        userLayoutPatternMap.setUser(user);
        userLayoutPatternMap.setLayoutPattern(layoutPattern);
        userLayoutPatternMap.setAcquisition(LocalDate.now());
        userLayoutPatternMap.setActive(false);

        return userLayoutPatternMap;
    }

    @Autowired
    public void setUserLayoutPatternMapDAO(UserLayoutPatternMapDAO userLayoutPatternMapDAO) {
        this.userLayoutPatternMapDAO = userLayoutPatternMapDAO;
    }
}
