package bean;

import bean.keys.UserLayoutPatternMapKey;
import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_layout_pattern_map")
public class UserLayoutPatternMap {

    @EmbeddedId
    UserLayoutPatternMapKey key;

    @Column(nullable = false)
    private LocalDate acquisition;

    @Column
    @Convert(converter = BooleanToYNConverter.class)
    private boolean active;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("layoutPatternId")
    @JoinColumn(name = "layout_pattern_id")
    LayoutPattern layoutPattern;

    public UserLayoutPatternMap(){}

    public UserLayoutPatternMapKey getKey() {
        return key;
    }

    public void setKey(UserLayoutPatternMapKey key) {
        this.key = key;
    }

    public LocalDate getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(LocalDate acquisition) {
        this.acquisition = acquisition;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLayoutPatternMap that)) return false;
        return active == that.active && key.equals(that.key) && acquisition.equals(that.acquisition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, acquisition, active);
    }
}
