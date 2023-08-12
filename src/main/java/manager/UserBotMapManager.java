package manager;

import bean.Bot;
import bean.User;
import bean.UserBotMap;
import dao.UserBotMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBotMapManager {

    private UserBotMapDAO userBotMapDAO;

    public void save(User user, List<Bot> bots) {
        UserBotMap userBotMap;

        for (Bot bot : bots) {
            userBotMap = new UserBotMap();
            userBotMap.setWin(false);
            userBotMap.setUser(user);
            userBotMap.setBot(bot);

            userBotMapDAO.save(userBotMap);
        }
    }

    @Autowired
    public void setUserBotMapDAO(UserBotMapDAO userBotMapDAO) {
        this.userBotMapDAO = userBotMapDAO;
    }
}
