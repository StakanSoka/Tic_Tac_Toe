package dao;

import bean.UserLayoutPatternMap;
import bean.key.UserLayoutPatternMapKey;

public class UserLayoutPatternMapDAO extends AbstractDAO<UserLayoutPatternMap, UserLayoutPatternMapKey> {

    @Override
    public Class<UserLayoutPatternMap> getEntityClass() {
        return UserLayoutPatternMap.class;
    }

    @Override
    public String getTableName() {
        return "user_layout_pattern_map";
    }
}
