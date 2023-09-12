package game.brain;

import game.elements.GamingField;

public interface Bot {

    int playRandomTurn(GamingField gamingField);

    int turn(GamingField gamingField) throws CloneNotSupportedException;
}
