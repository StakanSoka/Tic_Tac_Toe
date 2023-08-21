package bean;

import jakarta.persistence.*;
import util.BooleanToYNConverter;

import java.time.LocalDate;
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

    @Column(name = "active_for_player1")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean activeForPlayer1;

    @Column(name = "active_for_player2")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean activeForPlayer2;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    public UserSymbolMap(){}

    public boolean isActiveForPlayer1() {
        return activeForPlayer1;
    }

    public void setActiveForPlayer1(boolean activeForPlayer1) {
        this.activeForPlayer1 = activeForPlayer1;
    }

    public boolean isActiveForPlayer2() {
        return activeForPlayer2;
    }

    public void setActiveForPlayer2(boolean activeForPlayer2) {
        this.activeForPlayer2 = activeForPlayer2;
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
        return id == that.id && activeForPlayer1 == that.activeForPlayer1 && activeForPlayer2 == that.activeForPlayer2 && acquisition.equals(that.acquisition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acquisition, activeForPlayer1, activeForPlayer2);
    }
}
