package manager;

import bean.Symbol;
import bean.User;
import bean.UserSymbolMap;
import dao.UserSymbolMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class UserSymbolMapManager {

    private UserSymbolMapDAO userSymbolMapDAO;

    public void save(UserSymbolMap userSymbolMap) {
        userSymbolMapDAO.save(userSymbolMap);
    }

    public List<UserSymbolMap> create(User user, List<Symbol> symbols) {
        List<UserSymbolMap> userSymbolMaps = new ArrayList<>();
        UserSymbolMap userSymbolMap;

        for (Symbol symbol : symbols) {
            userSymbolMap = new UserSymbolMap();

            userSymbolMap.setUser(user);
            userSymbolMap.setSymbol(symbol);
            userSymbolMap.setActive(false);
            userSymbolMap.setAcquisition(LocalDate.now());
            userSymbolMap.setActivationDate(null);

            userSymbolMaps.add(userSymbolMap);
        }

        return userSymbolMaps;
    }

    public void activate(UserSymbolMap userSymbolMap) {
        userSymbolMap.setActive(true);
        userSymbolMap.setActivationDate(LocalDateTime.now());
    }

    public void deactivate(UserSymbolMap userSymbolMap) {
        userSymbolMap.setActive(false);
        userSymbolMap.setActivationDate(null);
    }

    @Autowired
    public void setUserSymbolMapDAO(UserSymbolMapDAO userSymbolMapDAO) {
        this.userSymbolMapDAO = userSymbolMapDAO;
    }
}