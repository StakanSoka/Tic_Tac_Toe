package bean;

import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.util.Objects;

@Entity
@Table(name = "user_bot_map")
public class UserBotMap {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Convert(converter = BooleanToYNConverter.class)
    private boolean win;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bot_id")
    private Bot bot;

    public UserBotMap() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBotMap that)) return false;
        return id == that.id && win == that.win;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, win);
    }
}
