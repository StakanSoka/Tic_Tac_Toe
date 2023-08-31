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
import java.util.stream.Collectors;

@Component
@Service
public class UserSymbolMapManager {

    private UserSymbolMapDAO userSymbolMapDAO;

    public void save(UserSymbolMap userSymbolMap) {
        userSymbolMapDAO.save(userSymbolMap);
    }

    public void update(UserSymbolMap userSymbolMap) {
        userSymbolMapDAO.update(userSymbolMap);
    }

    public UserSymbolMap findActiveSymbolForPlayer1(int userId) {
        List<UserSymbolMap> userSymbolMaps = userSymbolMapDAO.findAllByUserId(userId);

        for (UserSymbolMap userSymbolMap : userSymbolMaps) {
            if (userSymbolMap.isActiveForPlayer1()) return userSymbolMap;
        }

        return null;
    }

    public UserSymbolMap findActiveSymbolForPlayer2(int userId) {
        List<UserSymbolMap> userSymbolMaps = userSymbolMapDAO.findAllByUserId(userId);

        for (UserSymbolMap userSymbolMap : userSymbolMaps) {
            if (userSymbolMap.isActiveForPlayer2()) return userSymbolMap;
        }

        return null;
    }

    public UserSymbolMap find(int userId, int symbolId) {
        return userSymbolMapDAO.find(userId, symbolId);
    }

    public List<Symbol> findUserSymbols(User user) {
        List<UserSymbolMap> userSymbolMaps = userSymbolMapDAO.findAllByUserId(user.getId());
        List<Symbol> userSymbols = userSymbolMaps.stream()
                .map(UserSymbolMap::getSymbol)
                .collect(Collectors.toList());

        return userSymbols;
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

        return userSymbolMaps;
    }

    public List<Symbol> findActiveUserSymbolsByUserId(int userId) {
        List<UserSymbolMap> userSymbolMaps = userSymbolMapDAO.findAllByUserId(userId);
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