package util;

import bean.LayoutPattern;
import bean.Symbol;
import game.GameData;
import game.brain.Bot;
import game.brain.EasyBot;
import game.brain.NormalBot;
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

    public static List<Integer> findPossibleTurns(GamingField gamingField) {
        List<Integer> possibleTurns = new ArrayList<>();
        List<List<GamingCell>> gamingCells = gamingField.getCells();

        for (int i = 0; i < gamingCells.size(); i++) {
            for (int j = 0; j < gamingCells.get(i).size(); j++) {
                if (gamingCells.get(i).get(j).getStatement() == Constants.Game.NOBODY) {
                    possibleTurns.add(10 * i + j); // first number is row index second one is column index
                }
            }
        }

        return possibleTurns;
    }

    public static Bot findBotByDifficulty(String difficulty) {
        Bot bot;

        switch (difficulty) {
            case "easy" -> bot = new EasyBot();
            case "normal" -> bot = new NormalBot();
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

    public static void finishGame(GameData gameData, int winner) {
        gameData.setWinner(winner);
    }
}