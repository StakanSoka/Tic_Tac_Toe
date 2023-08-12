package manager;

import bean.Bot;
import bean.User;
import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserManager {

    UserDAO userDAO;

    public User create(String login, String password) {
        User user = new User();

        user.setLogin(login);
        user.setPassword(password);
        user.setRole("admin");
        user.setImage("root");
        user.setNickname(login);
        user.setRegistrationDate(LocalDate.now());
        user.setCoin(0);

        return user;
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
