package util;

import bean.LayoutPattern;
import bean.Symbol;

import java.util.List;

public class ShopUtil {

    public static int countTotalSum(List<Symbol> symbols, List<LayoutPattern> layoutPatterns) {
        int totalSum = 0;

        for (Symbol symbol : symbols) {
            totalSum += symbol.getPrice();
        }

        for (LayoutPattern layoutPattern : layoutPatterns) {
            totalSum += layoutPattern.getPrice();
        }

        return totalSum;
    }
}