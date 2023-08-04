package dao;

import bean.UserBotMap;
import bean.keys.UserBotMapKey;

public class UserBotMapDAO extends AbstractDAO<UserBotMap, UserBotMapKey> {

    @Override
    public Class<UserBotMap> getEntityClass() {
        return UserBotMap.class;
    }

    @Override
    public String getTableName() {
        return "user_bot_map";
    }
}
