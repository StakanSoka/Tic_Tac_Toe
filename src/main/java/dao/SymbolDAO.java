package dao;

import bean.Symbol;
import config.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class SymbolDAO extends AbstractDAO<Symbol, Integer> {

    public Symbol findBySymbol(char requestedSymbol) {
        Symbol databaseSymbol;
        HibernateFactoryConfig hibernateFactory = HibernateFactoryConfig.getInstance();
        SessionFactory sessionFactory = hibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        databaseSymbol = session.createNativeQuery("SELECT * FROM symbol WHERE symbol =:requestedSymbol",
                        getEntityClass())
                .setParameter("requestedSymbol", requestedSymbol).getSingleResult();

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
