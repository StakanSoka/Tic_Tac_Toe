package controller;

import bean.Bot;
import manager.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import util.GameUtil;

import java.util.List;

@Controller
public class HubController {

    private BotManager botManager;

    @GetMapping("/")
    public String greetingMessage(Model model) {
        List<Bot> bots;
        String pole = GameUtil.createPole().toString();

        bots = botManager.findAll();

        model.addAttribute("bots", bots);
        model.addAttribute("pole", pole);

        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }
}
