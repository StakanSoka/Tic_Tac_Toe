package application;

import bean.Bot;
import bean.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;

public class Run {

    public static User createTestUser() {
        User user = new User();
        user.setLogin("Natural");
        user.setPassword("123");
        user.setRole("odmen");
        user.setImage("path");
        user.setNickname("Natural");
        user.setRegistrationDate(LocalDate.now());

        return user;
    }

    public static Bot createTestBot() {
        Bot bot = new Bot();
        bot.setDifficulty("hard");
        bot.setImage("path");
        bot.setName("antoha");

        return bot;
    }

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.persist(createTestUser());
            session.persist(createTestBot());

            session.getTransaction().commit();
        }
    }
}
