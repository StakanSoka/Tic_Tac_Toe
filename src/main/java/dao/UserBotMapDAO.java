package dao;

import bean.UserBotMap;
import bean.key.UserBotMapKey;
import org.springframework.stereotype.Component;

@Component
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
