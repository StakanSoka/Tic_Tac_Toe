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

    private UserBotMapManager userBotMapManager;
    private UserManager userManager;
    private BotManager botManager;
    private UserLayoutPatternMapManager userLayoutPatternMapManager;
    private UserSymbolMapManager userSymbolMapManager;

    @GetMapping("/difficulty")
    public String chooseBotDifficulty(Authentication authentication, Model model) {
        User user = userManager.findByLogin(authentication.getName());
        List<UserBotMap> userBotMaps = userBotMapManager.findUserBotMaps(user.getId());

        model.addAttribute("bots", userBotMapManager.findUserBots(userBotMaps));
        return "game/difficulty";
    }

    @GetMapping("/start")
    public String startGame(@RequestParam("bot") Integer botId, Authentication authentication, Model model,
                            @ModelAttribute("gameData") GameData gameData) {
        User user = userManager.findByLogin(authentication.getName());
        Bot beanBot = botManager.findWithSymbolsAndLayoutPatterns(botId);
        game.brain.Bot brainBot = GameUtil.findBotByDifficulty(beanBot.getDifficulty());
        GamingField gamingField = new GamingField();

        int firstOrder = ((int)(Math.random() * 2)) % 2 == 0 ? Constants.Game.PLAYER : Constants.Game.BOT;
        LayoutPattern userActiveLayoutPattern = userLayoutPatternMapManager.findActiveLayoutPatternByUserId(user.getId());
        LayoutPattern botActiveLayoutPattern = beanBot.getLayoutPatterns().stream().findAny().orElse(null);
        List<Symbol> userActiveSymbols = userSymbolMapManager.findActiveUserSymbolByUserId(user.getId());
        List<Symbol> botActiveSymbols = beanBot.getSymbols();
        GameUtil.fillPlayersSettings(userActiveLayoutPattern, userActiveSymbols,
                botActiveLayoutPattern, botActiveSymbols, gamingField, firstOrder);

        gameData.setGamingField(gamingField);
        gameData.setBeanBot(beanBot);
        gameData.setBrainBot(brainBot);
        gameData.setWinner(GameStatuses.NOBODY);
        gameData.setGameStatuses(new GameStatuses());
        model.addAttribute("gameData", gameData);

        if (firstOrder == Constants.Game.BOT) {
            gamingField.update(brainBot.playRandomTurn(gamingField), Constants.Game.BOT);
        }

        return "game/game";
    }

    @GetMapping("/play")
    public String play(@RequestParam("playerTurn") String userTurn, @ModelAttribute("gameData") GameData gameData,
                       Model model) {
        int userCoordinates = Integer.parseInt(userTurn);
        GamingField gamingField = gameData.getGamingField();
        int winner = gameData.getWinner();
        model.addAttribute("gameData", gameData);

        if (winner != Constants.Game.NOBODY) { // user tries cheat through get request
            return "game/game";
        }

        gamingField.update(userCoordinates, Constants.Game.PLAYER);
        if (isGameFinished(gameData, gamingField)) return "game/game";

        gamingField.update(gameData.getBrainBot().playRandomTurn(gamingField), Constants.Game.BOT);
        isGameFinished(gameData, gamingField);

        return "game/game";
    }

    private boolean isGameFinished(@ModelAttribute("gameData") GameData gameData, GamingField gamingField) {
        int winner;
        winner = gamingField.checkWinner();
        if (winner != Constants.Game.NOBODY) {
            gameData.setWinner(winner);
            return true;
        }
        if (gamingField.isDraw()) {
            gameData.setWinner(Constants.Game.DRAW);
            return true;
        }
        return false;
    }

    @ModelAttribute("gameData")
    public GameData gamingFieldAttribute() {
        return new GameData();
    }

    @Autowired
    public void setUserBotMapManager(UserBotMapManager userBotMapManager) {
        this.userBotMapManager = userBotMapManager;
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