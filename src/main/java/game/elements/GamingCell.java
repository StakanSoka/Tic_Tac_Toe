package game.elements;

import bean.LayoutPattern;
import bean.Symbol;

public class GamingCell implements Cloneable {

    private int statement;
    private LayoutPattern layoutPattern;
    private Symbol symbol;

    @Override
    public GamingCell clone() throws CloneNotSupportedException {
        GamingCell clone = (GamingCell) super.clone();

        clone.setStatement(statement);
        clone.setSymbol(symbol);
        clone.setLayoutPattern(layoutPattern);

        return clone;
    }

    public int getStatement() {
        return statement;
    }

    public void setStatement(int statement) {
        this.statement = statement;
    }

    public LayoutPattern getLayoutPattern() {
        return layoutPattern;
    }

    public void setLayoutPattern(LayoutPattern layoutPattern) {
        this.layoutPattern = layoutPattern;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}