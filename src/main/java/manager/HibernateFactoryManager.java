package manager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.BooleanToYNConverter;

public class HibernateFactoryManager {

    private static volatile HibernateFactoryManager instance;
    private volatile SessionFactory sessionFactory;

    private HibernateFactoryManager(){}

    public static HibernateFactoryManager getInstance() {
        if (instance == null) {
            synchronized (HibernateFactoryManager.class) {
                if (instance == null) {
                    instance = new HibernateFactoryManager();
                }
            }
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateFactoryManager.class) {
                if (sessionFactory == null) {
                    Configuration configuration = new Configuration();
                    configuration.addAttributeConverter(BooleanToYNConverter.class);
                    sessionFactory = configuration.configure().buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

}
