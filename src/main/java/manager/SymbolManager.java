package manager;

import bean.Symbol;
import dao.SymbolDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import util.Constants;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class SymbolManager {

    private SymbolDAO symbolDAO;

    public List<Symbol> findBasedSymbols() {
        List<Symbol> symbols = new ArrayList<>();

        for (Character basedSymbol : Constants.BasedSymbols.getBasedSymbols()) {
            symbols.add(symbolDAO.findBySymbol(basedSymbol));
        }

        return symbols;
    }

    @Autowired
    public void setSymbolDAO(SymbolDAO symbolDAO) {
        this.symbolDAO = symbolDAO;
    }
}
