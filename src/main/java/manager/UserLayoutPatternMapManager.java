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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Service
public class UserLayoutPatternMapManager {

    private UserLayoutPatternMapDAO userLayoutPatternMapDAO;

    public UserLayoutPatternMap find(int userId, int layoutPatternId) {
        return userLayoutPatternMapDAO.find(userId, layoutPatternId);
    }

    public void save(UserLayoutPatternMap userLayoutPatternMap) {
        userLayoutPatternMapDAO.save(userLayoutPatternMap);
    }

    public List<LayoutPattern> findUserLayoutPatterns(User user) {
        List<UserLayoutPatternMap> userLayoutPatternMaps = userLayoutPatternMapDAO.findAllByUserId(user.getId());
        List<LayoutPattern> layoutPatterns = userLayoutPatternMaps.stream()
                .map(UserLayoutPatternMap::getLayoutPattern)
                .collect(Collectors.toList());

        return layoutPatterns;
    }

    public UserLayoutPatternMap findActiveLayoutPatternByUserId(int userId) {
        List<UserLayoutPatternMap> userLayoutPatternMaps = userLayoutPatternMapDAO.findAllByUserId(userId);
        Optional<UserLayoutPatternMap> userLayoutPatternMapOptional = userLayoutPatternMaps.stream().
                filter(UserLayoutPatternMap::isActive).
                findAny();

        return userLayoutPatternMapOptional.orElse(null);
    }

    public void update(UserLayoutPatternMap userLayoutPatternMap) {
        userLayoutPatternMapDAO.update(userLayoutPatternMap);
    }

    public UserLayoutPatternMap create(User user, LayoutPattern layoutPattern) {
        UserLayoutPatternMap userLayoutPatternMap = new UserLayoutPatternMap();

        userLayoutPatternMap.setUser(user);
        userLayoutPatternMap.setLayoutPattern(layoutPattern);
        userLayoutPatternMap.setAcquisition(LocalDate.now());
        userLayoutPatternMap.setActive(false);

        return userLayoutPatternMap;
    }

    public List<UserLayoutPatternMap> create(User user, List<LayoutPattern> layoutPatterns) {
        List<UserLayoutPatternMap> userLayoutPatternMaps = new ArrayList<>(layoutPatterns.size());

        for (LayoutPattern layoutPattern : layoutPatterns) {
            userLayoutPatternMaps.add(create(user, layoutPattern));
        }

        return userLayoutPatternMaps;
    }

    @Autowired
    public void setUserLayoutPatternMapDAO(UserLayoutPatternMapDAO userLayoutPatternMapDAO) {
        this.userLayoutPatternMapDAO = userLayoutPatternMapDAO;
    }
}
