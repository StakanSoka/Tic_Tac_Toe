package bean.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSymbolMapKey implements Serializable {

    @Column(name = "user_id")
    int userId;

    @Column(name = "symbol_id")
    int symbolId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(int symbolId) {
        this.symbolId = symbolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSymbolMapKey that)) return false;
        return userId == that.userId && symbolId == that.symbolId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, symbolId);
    }
}
