package bean;

import bean.keys.UserSymbolMapKey;
import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_symbol_map")
public class UserSymbolMap {

    @EmbeddedId
    private UserSymbolMapKey key;

    @Column(nullable = false)
    private LocalDate acquisition;

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean active;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("symbolId")
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    public UserSymbolMap(){}

    public UserSymbolMapKey getKey() {
        return key;
    }

    public void setKey(UserSymbolMapKey key) {
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

    public void setActive(boolean is_active) {
        this.active = is_active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSymbolMap that)) return false;
        return active == that.active && key.equals(that.key) && acquisition.equals(that.acquisition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, acquisition, active);
    }
}
