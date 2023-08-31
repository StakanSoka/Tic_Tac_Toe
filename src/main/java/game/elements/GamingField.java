package game.elements;

import bean.Symbol;
import util.Constants;
import util.GameUtil;

import java.util.ArrayList;
import java.util.List;

public class GamingField implements Cloneable {

    private StringBuilder field;
    private List<List<GamingCell>> cells;
    private String playerPositions;
    private Symbol playerSymbol;
    private String botPositions;
    private Symbol botSymbol;
    private int firstOrder;

    public GamingField() {
        field = GameUtil.createPole();
        cells = GameUtil.createGamingCells();
    }

    public void update(int coordinates, int turn) {
        int row = coordinates / 10;
        int column = coordinates % 10;
        String positions;
        Symbol symbol;
        int startI;
        int endI;
        int startJ;
        int endJ;
        char positionChar;

        if (turn == Constants.Game.PLAYER) {
            positions = playerPositions;
            symbol = playerSymbol;
        }
        else {
            positions = botPositions;
            symbol = botSymbol;
        }

        startI = (Constants.Game.POLE_WIDTH + 1) * ((Constants.Game.CELL_HEIGHT + 1) * row + 1) + (Constants.Game.CELL_WIDTH + 1) * column + 1;
        endI = startI + Constants.Game.POLE_WIDTH * Constants.Game.CELL_HEIGHT - Constants.Game.CELL_HEIGHT;
        startJ = 0;
        endJ = Constants.Game.CELL_WIDTH + 1;

        cells.get(row).get(column).setStatement(turn);
        cells.get(row).get(column).setSymbol(symbol);

        for (int i = startI, positionsK = 0; i < endI;) {
            for (int j = startJ; j < endJ; j++) {
                positionChar = positions.charAt(positionsK);
                if (positionChar == 'X') field.setCharAt(i + j, symbol.getSymbol());
                positionsK++;
            }

            i += Constants.Game.POLE_WIDTH + 1;
        }
    }

    public boolean isDraw() {

        for (int i = 0; i < cells.size(); i++) {
            for (int j = 0; j < cells.get(i).size(); j++) {
                if (cells.get(i).get(j).getStatement() == Constants.Game.NOBODY) {
                    return false;
                }
            }
        }

        return true;
    }

    public int checkWinner() {
        int result;

        for (int i = 0; i < cells.size(); i++) {
            result = cells.get(i).get(0).getStatement();
            for (int j = 1; j < cells.get(i).size(); j++) {
                if (cells.get(i).get(j).getStatement() != result) {
                    result = Constants.Game.NOBODY;
                    break;
                }
            }
            if (result != Constants.Game.NOBODY) return result;
        }

        for (int j = 0; j < cells.get(0).size(); j++) {
            result = cells.get(0).get(j).getStatement();
            for (int i = 1; i < cells.size(); i++) {
                if (cells.get(i).get(j).getStatement() != result) {
                    result = Constants.Game.NOBODY;
                    break;
                }
            }
            if (result != Constants.Game.NOBODY) return result;
        }

        result = cells.get(0).get(0).getStatement();
        for (int i = 1, j = 1; i < cells.size() && j < cells.size(); i++, j++) {
            if (result != cells.get(i).get(j).getStatement()) {
                result = Constants.Game.NOBODY;
                break;
            }
        }
        if (result != Constants.Game.NOBODY) return result;

        result = cells.get(0).get(cells.get(0).size() - 1).getStatement();
        for (int i = 1, j = cells.get(i).size() - 2; i < cells.size() && j >= 0; i++, j--) {
            if (result != cells.get(i).get(j).getStatement()) {
                result = Constants.Game.NOBODY;
                break;
            }
        }

        return result;
    }

    @Override
    public GamingField clone() throws CloneNotSupportedException {
        GamingField clonedGamingField = (GamingField) super.clone();
        List<List<GamingCell>> clonedGamingCells = new ArrayList<>();

        for (List<GamingCell> row : cells) {
            List<GamingCell> clonedRow = new ArrayList<>();
            for (GamingCell cell : row) {
                clonedRow.add(cell.clone());
            }
            clonedGamingCells.add(clonedRow);
        }

        clonedGamingField.setCells(clonedGamingCells);
        clonedGamingField.setField(new StringBuilder(field));

        return clonedGamingField;
    }

    public StringBuilder getField() {
        return field;
    }

    public void setField(StringBuilder field) {
        this.field = field;
    }

    public List<List<GamingCell>> getCells() {
        return cells;
    }

    public void setCells(List<List<GamingCell>> cells) {
        this.cells = cells;
    }

    public String getPlayerPositions() {
        return playerPositions;
    }

    public void setPlayerPositions(String playerPositions) {
        this.playerPositions = playerPositions;
    }

    public Symbol getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(Symbol playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String getBotPositions() {
        return botPositions;
    }

    public void setBotPositions(String botPositions) {
        this.botPositions = botPositions;
    }

    public Symbol getBotSymbol() {
        return botSymbol;
    }

    public void setBotSymbol(Symbol botSymbol) {
        this.botSymbol = botSymbol;
    }

    public int getFirstOrder() {
        return firstOrder;
    }

    public void setFirstOrder(int firstOrder) {
        this.firstOrder = firstOrder;
    }
}