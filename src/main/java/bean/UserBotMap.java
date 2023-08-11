package bean;

import bean.keys.UserBotMapKey;
import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.util.Objects;

@Entity
@Table(name = "user_bot_map")
public class UserBotMap {

    @EmbeddedId
    private UserBotMapKey key;

    @Column
    @Convert(converter = BooleanToYNConverter.class)
    private char win;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("botId")
    @JoinColumn(name = "bot_id")
    private Bot bot;

    public UserBotMap() {}

    public UserBotMapKey getKey() {
        return key;
    }

    public void setKey(UserBotMapKey key) {
        this.key = key;
    }

    public char getWin() {
        return win;
    }

    public void setWin(char win) {
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
        return win == that.win && key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, win);
    }
}
