package util;

import bean.LayoutPattern;
import bean.Symbol;
import game.brain.Bot;
import game.brain.EasyBot;
import game.elements.GamingCell;
import game.elements.GamingField;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {

    public static StringBuilder createPole() {
        StringBuilder pole = new StringBuilder();

        for (int i = 0; i < Constants.Game.POLE_HEIGHT; i++) {
            for (int j = 0; j < Constants.Game.POLE_WIDTH; j++) {
                if (i % (Constants.Game.CELL_HEIGHT + 1) == 0) {
                    pole.append('-');
                } else if (j % (Constants.Game.CELL_WIDTH + 1) == 0) {
                    pole.append('|');
                } else {
                    pole.append(' ');
                }
            }
            pole.append('\n');
        }

        return pole;
    }

    public static List<Integer> findPossibleCombinations(GamingField gamingField) {
        List<Integer> possibleCombinationsCoordinates = new ArrayList<>();
        List<List<GamingCell>> gamingCells = gamingField.getCells();

        for (int i = 0; i < gamingCells.size(); i++) {
            for (int j = 0; j < gamingCells.get(i).size(); j++) {
                if (gamingCells.get(i).get(j).getStatement() == Constants.Game.NOBODY) {
                    possibleCombinationsCoordinates.add(10 * i + j); // first number is row index second one is column index
                }
            }
        }

        return possibleCombinationsCoordinates;
    }

    public static Bot findBotByDifficulty(String difficulty) {
        Bot bot;

        switch (difficulty) {
            case "easy" -> bot = new EasyBot();
            default -> bot = null;
        }

        return bot;
    }

    public static List<List<GamingCell>> createGamingCells() {
        List<List<GamingCell>> gamingCells = new ArrayList<>();
        for (int i = 0; i < Constants.Game.NUMBER_CELL_COLUMN; i++) {
            gamingCells.add(new ArrayList<>());
            for (int j = 0; j < Constants.Game.NUMBER_CELL_ROW; j++) {
                gamingCells.get(i).add(new GamingCell());
            }
        }

        return gamingCells;
    }

    public static void fillPlayersSettings(LayoutPattern playerLayoutPattern, List<Symbol> playerSymbols,
                                           LayoutPattern botLayoutPattern, List<Symbol> botSymbols,
                                           GamingField gamingField, int firstPlayerOrder) {
        gamingField.setFirstOrder(firstPlayerOrder);
        if (firstPlayerOrder == Constants.Game.PLAYER) {
            gamingField.setPlayerPositions(playerLayoutPattern.getPosition1());
            gamingField.setPlayerSymbol(playerSymbols.get(0));
            gamingField.setBotPositions(botLayoutPattern.getPosition2());
            gamingField.setBotSymbol(botSymbols.get(1));
        }
        else {
            gamingField.setPlayerPositions(playerLayoutPattern.getPosition2());
            gamingField.setPlayerSymbol(playerSymbols.get(1));
            gamingField.setBotPositions(botLayoutPattern.getPosition1());
            gamingField.setBotSymbol(botSymbols.get(0));
        }
    }

    public static void insertCellIntoField(GamingField gamingField, Symbol symbol, String positions, int x, int y) {
        int startI = (Constants.Game.CELL_HEIGHT + 1) * x;
        int endI = startI + Constants.Game.CELL_HEIGHT;
        int startJ = (Constants.Game.CELL_WIDTH + 1) * y;
        int endJ = startJ + Constants.Game.CELL_WIDTH;
        StringBuilder field = gamingField.getField();
        char positionChar;
        int positionsK = 0;

        for (int i = startI; i < endI; i++) {
            for (int j = startJ; j < endJ; j++) {
                positionChar = positions.charAt(positionsK);
                if (positionChar == 'X') field.setCharAt(i + j, symbol.getSymbol());
                positionsK++;
            }
        }
    }

}