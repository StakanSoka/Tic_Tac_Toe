package controller;

import bean.*;
import dto.UserDTO;
import manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Constants;
import validator.UserValidator;

import javax.validation.Valid;
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

    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("userDTO", new UserDTO());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        User user;
        List<Bot> bots = botManager.findAll();
        List<Symbol> basedSymbols = symbolManager.findBasedSymbols();
        LayoutPattern basedLayoutPattern = layoutPatternManager.find(Constants.BasedLayoutPattern.ID);
        UserLayoutPatternMap userLayoutPatternMap;
        List<UserSymbolMap> userSymbolMaps;

        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "registration";
        }

        user = userManager.userDTOtoUserBean(userDTO);

        userManager.save(user);

        userBotMapManager.create(user, bots).forEach(userBotMap -> userBotMapManager.save(userBotMap));

        userSymbolMaps = userSymbolMapManager.create(user, basedSymbols);
        userSymbolMaps.get(0).setActiveForPlayer1(true); //we need to activate first two based symbols(x and 0)
        userSymbolMaps.get(1).setActiveForPlayer2(true);
        userSymbolMaps.forEach(basedSymbol -> userSymbolMapManager.save(basedSymbol));

        userLayoutPatternMap = userLayoutPatternMapManager.create(user, basedLayoutPattern);
        userLayoutPatternMap.setActive(true);

        userLayoutPatternMapManager.save(userLayoutPatternMap);

        return "redirect:/";
    }

    @GetMapping("/inventory")
    public String inventory(Model model, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        List<Symbol> symbols = userSymbolMapManager.findUserSymbols(user);
        List<LayoutPattern> layoutPatterns = userLayoutPatternMapManager.findUserLayoutPatterns(user);
        Symbol activatedSymbolForPlayer1 = userSymbolMapManager.findActiveSymbolForPlayer1(user.getId()).getSymbol();
        Symbol activatedSymbolForPlayer2 = userSymbolMapManager.findActiveSymbolForPlayer2(user.getId()).getSymbol();
        LayoutPattern activatedLayoutPattern = userLayoutPatternMapManager.findActiveLayoutPatternByUserId(user.getId()).getLayoutPattern();

        symbols.remove(activatedSymbolForPlayer1);
        symbols.remove(activatedSymbolForPlayer2);
        layoutPatterns.remove(activatedLayoutPattern);

        model.addAttribute("activatedSymbolForPlayer1", activatedSymbolForPlayer1);
        model.addAttribute("activatedSymbolForPlayer2", activatedSymbolForPlayer2);
        model.addAttribute("activatedLayoutPattern", activatedLayoutPattern);
        model.addAttribute("user", user);
        model.addAttribute("symbols", symbols);
        model.addAttribute("layoutPatterns", layoutPatterns);

        return "inventory";
    }

    @PostMapping("/inventory/set-active-symbol-player1")
    public String inventorySetActiveSymbolPlayer1(@RequestParam("symbolId") int symbolId, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        UserSymbolMap oldActiveSymbolMap = userSymbolMapManager.findActiveSymbolForPlayer1(user.getId());
        UserSymbolMap newActiveSymbolMap = userSymbolMapManager.find(user.getId(), symbolId);

        oldActiveSymbolMap.setActiveForPlayer1(false);
        userSymbolMapManager.update(oldActiveSymbolMap);

        newActiveSymbolMap.setActiveForPlayer1(true);
        userSymbolMapManager.update(newActiveSymbolMap);

        return "redirect:/user/inventory";
    }

    @PostMapping("/inventory/set-active-symbol-player2")
    public String inventorySetActiveSymbolPlayer2(@RequestParam("symbolId") int symbolId, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        UserSymbolMap oldActiveSymbolMap = userSymbolMapManager.findActiveSymbolForPlayer2(user.getId());
        UserSymbolMap newActiveSymbolMap = userSymbolMapManager.find(user.getId(), symbolId);

        oldActiveSymbolMap.setActiveForPlayer2(false);
        userSymbolMapManager.update(oldActiveSymbolMap);

        newActiveSymbolMap.setActiveForPlayer2(true);
        userSymbolMapManager.update(newActiveSymbolMap);

        return "redirect:/user/inventory";
    }

    @PostMapping("/inventory/set-active-layout-pattern")
    public String inventorySetActiveLayoutPattern(@RequestParam("layoutPatternId") int layoutPatternId, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        UserLayoutPatternMap oldActiveLayoutPatternMap = userLayoutPatternMapManager.findActiveLayoutPatternByUserId(user.getId());
        UserLayoutPatternMap newActiveLayoutPatternMap = userLayoutPatternMapManager.find(user.getId(), layoutPatternId);

        oldActiveLayoutPatternMap.setActive(false);
        userLayoutPatternMapManager.update(oldActiveLayoutPatternMap);

        newActiveLayoutPatternMap.setActive(true);
        userLayoutPatternMapManager.update(newActiveLayoutPatternMap);

        return "redirect:/user/inventory";
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

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
}