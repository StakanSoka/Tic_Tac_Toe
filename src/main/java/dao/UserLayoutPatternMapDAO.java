package dao;

import bean.UserLayoutPatternMap;
import org.springframework.stereotype.Component;

@Component
public class UserLayoutPatternMapDAO extends AbstractDAO<UserLayoutPatternMap, Integer> {

    @Override
    public Class<UserLayoutPatternMap> getEntityClass() {
        return UserLayoutPatternMap.class;
    }

    @Override
    public String getTableName() {
        return "user_layout_pattern_map";
    }
}
