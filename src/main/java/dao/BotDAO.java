package dao;

import bean.Bot;
import bean.LayoutPattern;
import bean.Symbol;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotDAO extends AbstractDAO<Bot, Integer> {

    public List<Symbol> findBotSymbols(int botId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String query = "SELECT symbol.* FROM symbol " +
                "JOIN bot_symbol_map bsm on symbol.id = bsm.symbol_id " +
                "WHERE bsm.bot_id = :botId";
        List<Symbol> symbols;

        session.beginTransaction();

        symbols = session.createNativeQuery(query, Symbol.class)
                .setParameter("botId", botId)
                .getResultList();

        session.getTransaction().commit();
        session.close();

        return symbols;
    }

    public List<LayoutPattern> findBotLayoutPatterns(int botId) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String query = "SELECT lp.* FROM layout_pattern lp " +
                "JOIN bot_layout_pattern_map blpm on lp.id = blpm.layout_pattern_id and blpm.bot_id = :botId";
        List<LayoutPattern> symbols;

        session.beginTransaction();

        symbols = session.createNativeQuery(query, LayoutPattern.class)
                .setParameter("botId", botId)
                .getResultList();

        session.getTransaction().commit();
        session.close();

        return symbols;
    }

    @Override
    public Class<Bot> getEntityClass() {
        return Bot.class;
    }

    @Override
    public String getTableName() {
        return "bot";
    }
}
