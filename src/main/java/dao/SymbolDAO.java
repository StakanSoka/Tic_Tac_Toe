package dao;

import bean.Symbol;
import config.HibernateFactoryConfig;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SymbolDAO extends AbstractDAO<Symbol, Integer> {

    @Override
    public void save(Symbol symbol) {
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.createNativeQuery("INSERT INTO symbol(symbol, price) VALUES (:symbol, :price)", Symbol.class)
                .setParameter("symbol", symbol.getSymbol())
                .setParameter("price", symbol.getPrice())
                .executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public Symbol findBySymbol(char requestedSymbol) {
        Symbol databaseSymbol;
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        try {
            databaseSymbol = session.createNativeQuery("SELECT * FROM symbol WHERE symbol =:requestedSymbol",
                            getEntityClass())
                    .setParameter("requestedSymbol", requestedSymbol).getSingleResult();

        } catch (NoResultException e) {
            databaseSymbol = null;
        }

        session.getTransaction().commit();
        session.close();

        return databaseSymbol;
    }

    @Override
    public Class<Symbol> getEntityClass() {
        return Symbol.class;
    }

    @Override
    public String getTableName() {
        return "symbol";
    }
}
