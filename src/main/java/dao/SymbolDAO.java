package dao;

import bean.Symbol;

public class SymbolDAO extends AbstractDAO<Symbol, Integer> {

    @Override
    public Class<Symbol> getEntityClass() {
        return Symbol.class;
    }

    @Override
    public String getTableName() {
        return "symbol";
    }
}
