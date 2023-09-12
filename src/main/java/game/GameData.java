package game;

import bean.Bot;
import util.GameStatuses;
import game.elements.GamingField;

public class GameData {
    private GamingField gamingField;
    private game.brain.Bot brainBot;
    private Bot beanBot;
    private int winner;
    private GameStatuses gameStatuses;
    private int botId;

    public GameStatuses getGameStatuses() {
        return gameStatuses;
    }

    public void setGameStatuses(GameStatuses gameStatuses) {
        this.gameStatuses = gameStatuses;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public GamingField getGamingField() {
        return gamingField;
    }

    public void setGamingField(GamingField gamingField) {
        this.gamingField = gamingField;
    }

    public game.brain.Bot getBrainBot() {
        return brainBot;
    }

    public void setBrainBot(game.brain.Bot brainBot) {
        this.brainBot = brainBot;
    }

    public Bot getBeanBot() {
        return beanBot;
    }

    public void setBeanBot(Bot beanBot) {
        this.beanBot = beanBot;
    }

    public int getBotId() {
        return botId;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }
}
