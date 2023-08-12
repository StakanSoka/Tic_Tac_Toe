package bean;

import javax.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table
@DiscriminatorValue("1")
public class Symbol extends Item {

    @Column
    private char symbol;

    @Column
    private int price;

    @OneToMany(mappedBy = "symbol")
    private Set<UserSymbolMap> userSymbolMaps;

    @ManyToMany(mappedBy = "symbols")
    private Set<Bot> bots;

    public Symbol(){}

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<UserSymbolMap> getUserSymbolMaps() {
        return userSymbolMaps;
    }

    public void setUserSymbolMaps(Set<UserSymbolMap> userSymbolMaps) {
        this.userSymbolMaps = userSymbolMaps;
    }

    public Set<Bot> getBots() {
        return bots;
    }

    public void setBots(Set<Bot> bots) {
        this.bots = bots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol symbol1)) return false;
        if (!super.equals(o)) return false;
        return symbol == symbol1.symbol && price == symbol1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), symbol, price);
    }
}
