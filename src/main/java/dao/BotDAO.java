package dao;

import bean.Bot;
import org.springframework.stereotype.Component;

@Component
public class BotDAO extends AbstractDAO<Bot, Integer> {

    @Override
    public Class<Bot> getEntityClass() {
        return Bot.class;
    }

    @Override
    public String getTableName() {
        return "bot";
    }
}
