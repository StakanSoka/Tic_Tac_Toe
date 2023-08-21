package manager;

import bean.LayoutPattern;
import bean.User;
import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
@Service
public class UserManager {

    private UserDAO userDAO;

    private PasswordEncoder passwordEncoder;

    public User create(String login, String password) {
        User user = new User();

        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        user.setImage("root");
        user.setNickname(login);
        user.setRegistrationDate(LocalDate.now());
        user.setCoin(0);

        return user;
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
