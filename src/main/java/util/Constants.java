package util;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static class OrderDetailTables {
        public final int SYMBOL = 1;
        public final int LAYOUT_PATTERN = 2;
    }

    public static class BasedSymbols {

        private static final List<Character> basedSymbols = Arrays.asList('X', 'O');

        public static List<Character> getBasedSymbols() {
            return basedSymbols;
        }
    }

    public static class BasedLayoutPattern {
        public static final int ID = 1;
    }
}
