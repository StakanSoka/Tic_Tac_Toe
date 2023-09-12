package manager;

import bean.OrderDetail;
import bean.Symbol;
import dao.SymbolDAO;
import dto.SymbolDTO;
import jakarta.persistence.criteria.Order;
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

    public List<Symbol> findAll() {
        return symbolDAO.findAll();
    }

    public List<Symbol> findByOrderDetails(List<OrderDetail> orderDetails) {
        List<Symbol> symbols;
        int symbolsSize = 0;

        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getTableId() == Constants.OrderDetailTables.SYMBOL) {
                symbolsSize++;
            }
        }

        symbols = new ArrayList<>(symbolsSize);

        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getTableId() == Constants.OrderDetailTables.SYMBOL) {
                symbols.add(symbolDAO.find(orderDetail.getItem().getId()));
            }
        }

        return symbols;
    }

    private Symbol createSymbol(char symbol, int price) {
        Symbol createdSymbol = new Symbol();
        createdSymbol.setSymbol(symbol);
        createdSymbol.setPrice(price);

        return createdSymbol;
    }

    public Symbol findBySymbol(Character symbol) {
        return symbolDAO.findBySymbol(symbol);
    }

    public Symbol symbolDTOtoSymbolBean(SymbolDTO symbolDTO) {
        return createSymbol(symbolDTO.getSymbol().charAt(0), symbolDTO.getPrice());
    }

    public Symbol findSymbol(int id) {
        return symbolDAO.find(id);
    }

    public void saveSymbol(Symbol symbol) {
        symbolDAO.save(symbol);
    }

    public void deleteSymbol(Symbol symbol) {
        symbolDAO.delete(symbol);
    }

    public void updateSymbol(Symbol symbol) {
        symbolDAO.update(symbol);
    }

    @Autowired
    public void setSymbolDAO(SymbolDAO symbolDAO) {
        this.symbolDAO = symbolDAO;
    }
}
