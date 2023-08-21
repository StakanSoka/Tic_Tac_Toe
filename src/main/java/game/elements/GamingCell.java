package game.elements;

import bean.LayoutPattern;
import bean.Symbol;
import exception.NoSuchCellStatementException;
import util.Constants;

public class GamingCell {

    private int statement;
    private LayoutPattern layoutPattern;
    private Symbol symbol;

    @Override
    public GamingCell clone() throws CloneNotSupportedException {
        GamingCell clone = (GamingCell) super.clone();

        clone.setStatement(statement);

        return clone;
    }

    public void changeStatement(int newStatement) throws NoSuchCellStatementException {
        if (newStatement != Constants.Game.NOBODY && newStatement != Constants.Game.PLAYER && newStatement != Constants.Game.BOT) {
            throw new NoSuchCellStatementException();
        }
        statement = newStatement;
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