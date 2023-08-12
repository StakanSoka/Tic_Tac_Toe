package dao;

import bean.UserSymbolMap;
import bean.key.UserSymbolMapKey;

public class UserSymbolMapDAO extends AbstractDAO<UserSymbolMap, UserSymbolMapKey> {

    @Override
    public Class<UserSymbolMap> getEntityClass() {
        return UserSymbolMap.class;
    }

    @Override
    public String getTableName() {
        return "user_symbol_map";
    }
}
