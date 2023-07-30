package bean.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserBotMapKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "bot_id")
    private int botId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBotId() {
        return botId;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBotMapKey that)) return false;
        return userId == that.userId && botId == that.botId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, botId);
    }
}