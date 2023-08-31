package dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class SymbolDTO {

    @NotBlank(message = "Symbol cannot be empty")
    @Size(min = 1, max = 1, message = "Symbol must be a single character")
    private String symbol;

    @PositiveOrZero(message = "Price must not be a negative value")
    private int price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
