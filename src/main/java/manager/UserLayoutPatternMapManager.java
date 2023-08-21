package manager;

import bean.LayoutPattern;
import bean.User;
import bean.UserLayoutPatternMap;
import dao.LayoutPatternDAO;
import dao.UserLayoutPatternMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class UserLayoutPatternMapManager {

    private UserLayoutPatternMapDAO userLayoutPatternMapDAO;

    public void save(UserLayoutPatternMap userLayoutPatternMap) {
        userLayoutPatternMapDAO.save(userLayoutPatternMap);
    }

    public LayoutPattern findActiveLayoutPatternByUserId(int userId) {
        List<UserLayoutPatternMap> userLayoutPatternMaps = userLayoutPatternMapDAO.findAllByUserId(userId);
        Optional<UserLayoutPatternMap> userLayoutPatternMapOptional = userLayoutPatternMaps.stream().
                filter(UserLayoutPatternMap::isActive).
                findAny();
        UserLayoutPatternMap userLayoutPatternMap = userLayoutPatternMapOptional.orElse(null);

        if (userLayoutPatternMap == null) return null;
        return userLayoutPatternMap.getLayoutPattern();
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
