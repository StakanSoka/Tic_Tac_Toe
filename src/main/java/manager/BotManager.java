package manager;

import bean.Bot;
import dao.BotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class BotManager {

    private BotDAO botDAO;

    public Bot find(int id) {
        return botDAO.find(id);
    }

    public List<Bot> findAll() {
        return botDAO.findAll();
    }

    public List<Bot> findAllWithSymbolsAndLayoutPatterns() {
        List<Bot> bots = botDAO.findAll();

        for (Bot bot : bots) {
            bot.setSymbols(botDAO.findBotSymbols(bot.getId()));
            bot.setLayoutPatterns(botDAO.findBotLayoutPatterns(bot.getId()));
        }

        return bots;
    }

    public Bot findWithSymbolsAndLayoutPatterns(int id) {
        Bot bot = botDAO.find(id);

        bot.setSymbols(botDAO.findBotSymbols(id));
        bot.setLayoutPatterns(botDAO.findBotLayoutPatterns(id));

        return bot;
    }

    @Autowired
    public void setBotDAO(BotDAO botDAO) {
        this.botDAO = botDAO;
    }
}
