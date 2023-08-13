package dao;

import bean.UserSymbolMap;
import org.springframework.stereotype.Component;

@Component
public class UserSymbolMapDAO extends AbstractDAO<UserSymbolMap, Integer> {

    @Override
    public Class<UserSymbolMap> getEntityClass() {
        return UserSymbolMap.class;
    }

    @Override
    public String getTableName() {
        return "user_symbol_map";
    }
}
