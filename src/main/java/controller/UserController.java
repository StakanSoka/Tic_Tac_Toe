package controller;

import bean.Bot;
import bean.User;
import manager.BotManager;
import manager.UserBotMapManager;
import manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserManager userManager;
    private BotManager botManager;
    private UserBotMapManager userBotMapManager;

    @GetMapping("/create")
    public String create() {
        return "form";
    }

    @PostMapping("/create")
    public String create(@RequestParam("login") String login, @RequestParam("password") String password) {
        User user = userManager.create(login, password);
        List<Bot> bots = botManager.findAll();

        userManager.save(user);
        user = userManager.findByLogin(user.getLogin());
        userBotMapManager.save(user, bots);

        return "redirect:/";
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
}
