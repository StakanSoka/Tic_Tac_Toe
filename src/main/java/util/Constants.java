package util;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static class OrderDetailTables {
        public static final int SYMBOL = 1;
        public static final int LAYOUT_PATTERN = 2;
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

    public static class Game {
        public static final int NOBODY = 0;
        public static final int PLAYER = 1;
        public static final int BOT = 2;
        public static final int DRAW = 3;

        public static final int NUMBER_CELL_ROW = 3;
        public static final int NUMBER_CELL_COLUMN = 3;

        public static final int POLE_WIDTH = 37;
        public static final int POLE_HEIGHT = 19;
        public static final int CELL_HEIGHT = 5;
        public static final int CELL_WIDTH = 11;
    }
}
