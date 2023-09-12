package game.brain;

import game.elements.GamingField;

public class EasyBot extends AbstractBot {

    @Override
    public int turn(GamingField gamingField) throws CloneNotSupportedException {
        GamingField copiedGamingField = gamingField.clone();

        return playRandomTurn(gamingField);
    }
}
