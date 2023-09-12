package game.brain;

import game.elements.GamingField;
import util.Constants;
import util.GameUtil;

import java.util.List;

public class NormalBot extends AbstractBot {

    @Override
    public int turn(GamingField gamingField) throws CloneNotSupportedException {
        GamingField clonedGamingFiled = gamingField.clone();

        List<Integer> possibleTurns = GameUtil.findPossibleTurns(clonedGamingFiled);

        for (int possibleTurn : possibleTurns) {
            clonedGamingFiled.update(possibleTurn, Constants.Game.BOT);

            if (clonedGamingFiled.checkWinner() == Constants.Game.BOT) {
                return possibleTurn;
            }

            clonedGamingFiled = gamingField.clone();
        }

        for (int possibleTurn : possibleTurns) {
            clonedGamingFiled.update(possibleTurn, Constants.Game.PLAYER);

            if (clonedGamingFiled.checkWinner() == Constants.Game.PLAYER) {
                return possibleTurn;
            }

            clonedGamingFiled = gamingField.clone();
        }

        return playRandomTurn(gamingField);
    }
}
