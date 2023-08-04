package dao;

public class BotDAO extends AbstractDAO<BotDAO, Integer> {

    @Override
    public Class<BotDAO> getEntityClass() {
        return BotDAO.class;
    }

    @Override
    public String getTableName() {
        return "bot";
    }
}
