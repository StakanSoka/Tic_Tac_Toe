package config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.BooleanToYNConverter;

public class HibernateFactoryConfig {

    private static volatile HibernateFactoryConfig instance;
    private volatile SessionFactory sessionFactory;

    private HibernateFactoryConfig(){}

    public static HibernateFactoryConfig getInstance() {
        if (instance == null) {
            synchronized (HibernateFactoryConfig.class) {
                if (instance == null) {
                    instance = new HibernateFactoryConfig();
                }
            }
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateFactoryConfig.class) {
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
