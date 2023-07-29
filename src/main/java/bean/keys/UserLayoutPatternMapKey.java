package bean.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserLayoutPatternMapKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "layout_pattern_id")
    private int layoutPatternId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLayoutPatternId() {
        return layoutPatternId;
    }

    public void setLayoutPatternId(int layoutPatternId) {
        this.layoutPatternId = layoutPatternId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLayoutPatternMapKey that)) return false;
        return userId == that.userId && layoutPatternId == that.layoutPatternId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, layoutPatternId);
    }
}
