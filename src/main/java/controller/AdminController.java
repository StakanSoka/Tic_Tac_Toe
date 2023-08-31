package controller;

import bean.Bot;
import bean.LayoutPattern;
import bean.Symbol;
import bean.User;
import dto.SymbolDTO;
import manager.BotManager;
import manager.LayoutPatternManager;
import manager.SymbolManager;
import manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import util.AdminUtil;
import util.Constants;
import validator.SymbolValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserManager userManager;
    private BotManager botManager;
    private SymbolManager symbolManager;
    private LayoutPatternManager layoutPatternManager;

    private SymbolValidator symbolValidator;


    @GetMapping("/users")
    public String userInfo(Model model) {
        List<User> users = userManager.findAll();

        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("userId") int userId) {

        userManager.deleteById(userId);

        return "redirect:/admin/users";
    }

    @GetMapping("/symbols")
    public String symbols(Model model) {
        List<Symbol> allSymbols = symbolManager.findAll();
        List<Bot> bots = botManager.findAllWithSymbolsAndLayoutPatterns();

        Set<Symbol> botSymbols = new HashSet<>();
        List<Symbol> shopSymbols = new ArrayList<>();

        for (Bot bot : bots) {
            botSymbols.addAll(bot.getSymbols());
        }

        for (Symbol symbol : allSymbols) {
            if (!botSymbols.contains(symbol)) {
                shopSymbols.add(symbol);
            }
        }

        model.addAttribute("operation", "operation");
        model.addAttribute("shopSymbols", shopSymbols);
        model.addAttribute("symbolDTO", new SymbolDTO());

        return "symbols";
    }

    @PostMapping("/delete-symbol")
    public String deleteSymbol(@RequestParam("symbolId") int symbolId) {
        Symbol symbol = symbolManager.findSymbol(symbolId);

        symbolManager.deleteSymbol(symbol);

        return "redirect:/admin/symbols";
    }

    @PostMapping("/create-symbol")
    public String createSymbol(@ModelAttribute @Valid SymbolDTO symbolDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Symbol symbol = symbolManager.symbolDTOtoSymbolBean(symbolDTO);

        symbolValidator.validate(symbolDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder totalErrors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                totalErrors.append(error.getDefaultMessage()).append(';');
            }
            redirectAttributes.addFlashAttribute("symbolDTOError", totalErrors.toString());
            return "redirect:/admin/symbols";
        }

        symbolManager.saveSymbol(symbol);

        return "redirect:/admin/symbols";
    }

    @PostMapping("/change-symbol-price")
    public String changeSymbolPrice(@RequestParam("symbolId") int symbolId, @RequestParam String operation,
                                    @RequestParam("amount") int amount, RedirectAttributes redirectAttributes) {
        Symbol symbol = symbolManager.findSymbol(symbolId);
        switch (operation) {
            case "set" -> symbol.setPrice(amount);

            case "add" -> symbol.setPrice(symbol.getPrice() + amount);

            case "reduce" -> symbol.setPrice(symbol.getPrice() - amount);
        }

        if (symbol.getPrice() < 0) {
            redirectAttributes.addFlashAttribute("priceError", "Price must be more or equal to 0");
            return "redirect:/admin/symbols";
        }

        symbolManager.updateSymbol(symbol);

        return "redirect:/admin/symbols";
    }

    @PostMapping("/change-user-coins")
    public String changeUserCoins(@RequestParam("userId") int userId, @RequestParam String operation,
                                  @RequestParam("amount") int amount) {
        User user = userManager.find(userId);

        switch (operation) {
            case "set" -> user.setCoin(amount);

            case "add" -> user.setCoin(user.getCoin() + amount);

            case "reduce" -> user.setCoin(user.getCoin() - amount);
        }

        userManager.update(user);

        return "redirect:/admin/users";
    }

    @GetMapping("/layout-patterns")
    public String layoutPatterns(Model model) {
        List<LayoutPattern> allLayoutPatterns = layoutPatternManager.findAll();
        List<Bot> bots = botManager.findAllWithSymbolsAndLayoutPatterns();

        Set<LayoutPattern> botLayoutPatterns = new HashSet<>();
        List<LayoutPattern> shopLayoutPatterns = new ArrayList<>();

        for (Bot bot : bots) {
            botLayoutPatterns.addAll(bot.getLayoutPatterns());
        }

        for (LayoutPattern layoutPattern : allLayoutPatterns) {
            if (!botLayoutPatterns.contains(layoutPattern)) {
                shopLayoutPatterns.add(layoutPattern);
            }
        }

        model.addAttribute("shopLayoutPatterns", shopLayoutPatterns);

        return "layout-patterns";
    }

    @GetMapping("/layout-patterns/redaction")
    public String layoutPatternsRedaction(@RequestParam(value = "position1", required = false) StringBuilder position1,
                                       @RequestParam(value = "position2", required = false) StringBuilder position2,
                                       @RequestParam(value = "changedPosition", required = false) Integer changedPosition,
                                       @RequestParam(value = "y", required = false) Integer y,
                                       @RequestParam(value = "x", required = false) Integer x,
                                       Model model) {
        int coordinate;
        if (position1 == null) {
            position1 = AdminUtil.createPosition();
        }
        if (position2 == null) {
            position2 = AdminUtil.createPosition();
        }

        if (x == null || y == null || x < 0 || x >= Constants.Game.CELL_WIDTH || y < 0 || y > Constants.Game.CELL_HEIGHT) {
            AdminUtil.putDataToModel(position1, position2, model);
            return "layout-patterns-redaction";
        }

        coordinate = y * (Constants.Game.CELL_WIDTH + 2) + x; //+2 cause there are '\n' and '\r' symbols
        if (changedPosition != null && changedPosition == AdminUtil.POSITION1) {
            if (position1.charAt(coordinate) == 'X') {
                position1.setCharAt(coordinate, ' '); // second click to clear symbol here
            }
            else {
                position1.setCharAt(coordinate, 'X'); // 'X' based symbol for layout patterns
            }
        }
        else if (changedPosition != null && changedPosition == AdminUtil.POSITION2) {
            if (position2.charAt(coordinate) == 'X') {
                position2.setCharAt(coordinate, ' ');
            }
            else {
                position2.setCharAt(coordinate, 'X');
            }
        }

        AdminUtil.putDataToModel(position1, position2, model);

        return "layout-patterns-redaction";
    }

    @PostMapping("/create-layout-pattern")
    public String createLayoutPattern(@RequestParam("position1") StringBuilder position1, @RequestParam("position2") StringBuilder position2,
                                      @RequestParam("price") int price) {
        LayoutPattern layoutPattern;

        AdminUtil.formatPosition(position1);
        AdminUtil.formatPosition(position2);

        layoutPattern = layoutPatternManager.createLayoutPattern(position1.toString(), position2.toString(), price);
        layoutPatternManager.save(layoutPattern);

        return "redirect:/admin/layout-patterns";
    }

    @PostMapping("change-layout-pattern-price")
    public String changeLayoutPatternPrice(@RequestParam("layoutPatternId") int layoutPatternId,
                                           @RequestParam("operation") String operation, @RequestParam int amount,
                                           RedirectAttributes redirectAttributes) {
        LayoutPattern layoutPattern = layoutPatternManager.find(layoutPatternId);

        switch (operation) {
            case "set" -> layoutPattern.setPrice(amount);

            case "add" -> layoutPattern.setPrice(layoutPattern.getPrice() + amount);

            case "reduce" -> layoutPattern.setPrice(layoutPattern.getPrice() - amount);
        }

        if (layoutPattern.getPrice() < 0) {
            redirectAttributes.addFlashAttribute("priceError", "Price cannot be less then 0");
            return "redirect:/admin/layout-patterns";
        }

        layoutPatternManager.update(layoutPattern);

        return "redirect:/admin/layout-patterns";
    }

    @PostMapping("/delete-layout-pattern")
    public String deleteLayoutPattern(@RequestParam("layoutPatternId") int layoutPatternId) {
        LayoutPattern layoutPattern = layoutPatternManager.find(layoutPatternId);

        layoutPatternManager.delete(layoutPattern);

        return "redirect:/admin/layout-patterns";
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
    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    @Autowired
    public void setSymbolValidator(SymbolValidator symbolValidator) {
        this.symbolValidator = symbolValidator;
    }

    @Autowired
    public void setLayoutPatternManager(LayoutPatternManager layoutPatternManager) {
        this.layoutPatternManager = layoutPatternManager;
    }
}