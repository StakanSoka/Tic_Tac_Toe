package bean;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Symbol {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private char symbol;

    @Column
    private int price;

    @OneToMany(mappedBy = "symbol")
    Set<UserSymbolMap> userSymbolMaps;

    @ManyToMany(mappedBy = "symbols")
    Set<Bot> bots;

    public Symbol(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol symbol1)) return false;
        return id == symbol1.id && symbol == symbol1.symbol && price == symbol1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, price);
    }
}
