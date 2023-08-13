package bean;

import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_symbol_map")
public class UserSymbolMap {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate acquisition;

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean active;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    public UserSymbolMap(){}

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

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
        return id == that.id && active == that.active && acquisition.equals(that.acquisition) && Objects.equals(activationDate, that.activationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acquisition, active, activationDate);
    }
}
