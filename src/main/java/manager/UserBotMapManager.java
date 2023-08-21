package manager;

import bean.Bot;
import bean.User;
import bean.UserBotMap;
import dao.UserBotMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Service
public class UserBotMapManager {

    private UserBotMapDAO userBotMapDAO;

    public Set<UserBotMap> create(User user, List<Bot> bots) {
        Set<UserBotMap> userBotMaps = new HashSet<>();
        UserBotMap userBotMap;

        for (Bot bot : bots) {
            userBotMap = new UserBotMap();

            userBotMap.setWin(false);
            userBotMap.setUser(user);
            userBotMap.setBot(bot);

            userBotMaps.add(userBotMap);
        }

        return userBotMaps;
    }

    public List<UserBotMap> findUserBotMaps(int userId) {
        return userBotMapDAO.findByUserId(userId);
    }

    public List<Bot> findUserBots(List<UserBotMap> userBotMaps) {
        List<Bot> userBots = new ArrayList<>();

        for (UserBotMap userBotMap : userBotMaps) {
            userBots.add(userBotMap.getBot());
        }

        return userBots;
    }

    public void save(UserBotMap userBotMap) {
        userBotMapDAO.save(userBotMap);
    }

    @Autowired
    public void setUserBotMapDAO(UserBotMapDAO userBotMapDAO) {
        this.userBotMapDAO = userBotMapDAO;
    }
}
