package manager;

import bean.Symbol;
import bean.User;
import bean.UserSymbolMap;
import dao.UserSymbolMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            userSymbolMap.setAcquisition(LocalDate.now());

            userSymbolMaps.add(userSymbolMap);
        }

        userSymbolMaps.get(0).setActiveForPlayer1(true); //we need to activate first two based symbols(x and 0)
        userSymbolMaps.get(1).setActiveForPlayer2(true);

        return userSymbolMaps;
    }

    public List<Symbol> findActiveUserSymbolByUserId(int userId) {
        List<UserSymbolMap> userSymbolMaps = userSymbolMapDAO.findByUserId(userId);
        List<Symbol> activatedSymbols = new ArrayList<>(userSymbolMaps.size());

        for (UserSymbolMap userSymbolMap : userSymbolMaps) {
            if (userSymbolMap.isActiveForPlayer1()) {
                activatedSymbols.add(userSymbolMap.getSymbol());
                break;
            }
        }

        for (UserSymbolMap userSymbolMap : userSymbolMaps) {
            if (userSymbolMap.isActiveForPlayer2()) {
                activatedSymbols.add(userSymbolMap.getSymbol());
                break;
            }
        }

        return activatedSymbols;
    }

    @Autowired
    public void setUserSymbolMapDAO(UserSymbolMapDAO userSymbolMapDAO) {
        this.userSymbolMapDAO = userSymbolMapDAO;
    }
}