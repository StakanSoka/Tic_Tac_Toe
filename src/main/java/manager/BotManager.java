package manager;

import bean.Bot;
import dao.BotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotManager {

    private BotDAO botDAO;

    public List<Bot> findAll() {
        return botDAO.findAll();
    }

    @Autowired
    public void setBotDAO(BotDAO botDAO) {
        this.botDAO = botDAO;
    }
}
