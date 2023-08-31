package controller;

import bean.*;
import game.GameData;
import game.elements.GamingField;
import manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import util.Constants;
import util.GameStatuses;
import util.GameUtil;

import java.util.List;

@Controller
@RequestMapping("/game")
@SessionAttributes("gameData")
public class GameController {

    private UserManager userManager;
    private BotManager botManager;
    private UserLayoutPatternMapManager userLayoutPatternMapManager;
    private UserSymbolMapManager userSymbolMapManager;

    @GetMapping("/start")
    public String startGame(@RequestParam("botId") Integer botId, Authentication authentication, Model model,
                            @ModelAttribute("gameData") GameData gameData) {
        User user = userManager.findByLogin(authentication.getName());
        Bot beanBot = botManager.findWithSymbolsAndLayoutPatterns(botId);
        game.brain.Bot brainBot = GameUtil.findBotByDifficulty(beanBot.getDifficulty());
        GamingField gamingField = new GamingField();

        int firstOrder = ((int)(Math.random() * 2)) % 2 == 0 ? Constants.Game.PLAYER : Constants.Game.BOT;
        LayoutPattern userActiveLayoutPattern = userLayoutPatternMapManager.findActiveLayoutPatternByUserId(user.getId()).getLayoutPattern();
        LayoutPattern botActiveLayoutPattern = beanBot.getLayoutPatterns().stream().findAny().orElse(null);
        List<Symbol> userActiveSymbols = userSymbolMapManager.findActiveUserSymbolsByUserId(user.getId());
        List<Symbol> botActiveSymbols = beanBot.getSymbols();
        GameUtil.fillPlayersSettings(userActiveLayoutPattern, userActiveSymbols,
                botActiveLayoutPattern, botActiveSymbols, gamingField, firstOrder);

        gameData.setGamingField(gamingField);
        gameData.setBeanBot(beanBot);
        gameData.setBrainBot(brainBot);
        gameData.setWinner(GameStatuses.NOBODY);
        gameData.setGameStatuses(new GameStatuses());
        gameData.setBotId(botId);
        model.addAttribute("gameData", gameData);

        if (firstOrder == Constants.Game.BOT) {
            gamingField.update(brainBot.playRandomTurn(gamingField), Constants.Game.BOT);
        }

        return "game";
    }

    @GetMapping("/play")
    public String play(@RequestParam("playerTurn") String userTurn, @ModelAttribute("gameData") GameData gameData,
                       Authentication authentication, Model model) throws CloneNotSupportedException {
        User user;
        int userCoordinates = Integer.parseInt(userTurn);
        GamingField gamingField = gameData.getGamingField();

        model.addAttribute("gameData", gameData);

        if (gameData.getWinner() != Constants.Game.NOBODY) { // user tries cheat through get request
            return "game";
        }
        if (!GameUtil.findPossibleTurns(gamingField).contains(userCoordinates)) {
            return "game";
        }


        gamingField.update(userCoordinates, Constants.Game.PLAYER);
        if (gamingField.checkWinner() == Constants.Game.PLAYER) {
            GameUtil.finishGame(gameData, Constants.Game.PLAYER);

            user = userManager.findByLogin(authentication.getName());
            user.setCoin(user.getCoin() + gameData.getBeanBot().getCoin());
            userManager.update(user);

            return "game";
        } else if (gamingField.isDraw()) {
            GameUtil.finishGame(gameData, Constants.Game.DRAW);
            return "game";
        }

        gamingField.update(gameData.getBrainBot().turn(gamingField), Constants.Game.BOT);
        if (gamingField.checkWinner() == Constants.Game.BOT) {
            GameUtil.finishGame(gameData, Constants.Game.BOT);
        } else if (gamingField.isDraw()) {
            GameUtil.finishGame(gameData, Constants.Game.DRAW);
        }

        return "game";
    }

    @ModelAttribute("gameData")
    public GameData gameDataAttribute() {
        return new GameData();
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setUserLayoutPatternMapManager(UserLayoutPatternMapManager userLayoutPatternMapManager) {
        this.userLayoutPatternMapManager = userLayoutPatternMapManager;
    }

    @Autowired
    public void setUserSymbolMapManager(UserSymbolMapManager userSymbolMapManager) {
        this.userSymbolMapManager = userSymbolMapManager;
    }

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }
}