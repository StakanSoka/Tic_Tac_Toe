package game.brain;

import game.elements.GamingField;
import util.GameUtil;

import java.util.List;

public abstract class AbstractBot implements Bot {

    @Override
    public int playRandomTurn(GamingField gamingField) {
        List<Integer> possibleCombinations = GameUtil.findPossibleTurns(gamingField);
        int randomCoordinate = (int) (Math.random() * possibleCombinations.size());

        return possibleCombinations.get(randomCoordinate);
    }
}
