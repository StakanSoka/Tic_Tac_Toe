package bean;

import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_layout_pattern_map")
public class UserLayoutPatternMap {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate acquisition;

    @Column
    @Convert(converter = BooleanToYNConverter.class)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "layout_pattern_id")
    private LayoutPattern layoutPattern;

    public UserLayoutPatternMap(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LayoutPattern getLayoutPattern() {
        return layoutPattern;
    }

    public void setLayoutPattern(LayoutPattern layoutPattern) {
        this.layoutPattern = layoutPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLayoutPatternMap that)) return false;
        return id == that.id && active == that.active && acquisition.equals(that.acquisition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acquisition, active);
    }
}
