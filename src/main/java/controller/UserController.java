package controller;

import bean.*;
import manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import util.Constants;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserManager userManager;
    private BotManager botManager;
    private UserBotMapManager userBotMapManager;
    private SymbolManager symbolManager;
    private UserSymbolMapManager userSymbolMapManager;
    private LayoutPatternManager layoutPatternManager;
    private UserLayoutPatternMapManager userLayoutPatternMapManager;


    @GetMapping("/create")
    public String create() {
        return "form";
    }

    @PostMapping("/create")
    public String create(@RequestParam("login") String login, @RequestParam("password") String password) {
        User user = userManager.create(login, password);
        List<Bot> bots = botManager.findAll();
        List<Symbol> basedSymbols = symbolManager.findBasedSymbols();
        LayoutPattern basedLayoutPattern = layoutPatternManager.find(Constants.BasedLayoutPattern.ID);

        userManager.save(user);

        userBotMapManager.create(user, bots).forEach(userBotMap -> userBotMapManager.save(userBotMap));

        userSymbolMapManager.create(user, basedSymbols).forEach(
                basedSymbol -> {
            userSymbolMapManager.activate(basedSymbol);
            userSymbolMapManager.save(basedSymbol);
        });

        userLayoutPatternMapManager.save(userLayoutPatternMapManager.create(user, basedLayoutPattern));

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/hello")
    public String helloUser() {
        return "hello-user";
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }

    @Autowired
    public void setUserBotMapManager(UserBotMapManager userBotMapManager) {
        this.userBotMapManager = userBotMapManager;
    }

    @Autowired
    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    @Autowired
    public void setUserSymbolMapManager(UserSymbolMapManager userSymbolMapManager) {
        this.userSymbolMapManager = userSymbolMapManager;
    }

    @Autowired
    public void setLayoutPatternManager(LayoutPatternManager layoutPatternManager) {
        this.layoutPatternManager = layoutPatternManager;
    }

    @Autowired
    public void setUserLayoutPatternMapManager(UserLayoutPatternMapManager userLayoutPatternMapManager) {
        this.userLayoutPatternMapManager = userLayoutPatternMapManager;
    }
}