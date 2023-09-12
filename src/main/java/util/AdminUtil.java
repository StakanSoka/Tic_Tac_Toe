package util;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class AdminUtil {

    public static int POSITION1 = 1;
    public static int POSITION2 = 2;

    public static StringBuilder createPosition() {
        StringBuilder stringBuilder = new StringBuilder(Constants.Game.CELL_HEIGHT * (Constants.Game.CELL_WIDTH + 1)); // +1 because we must count '\n' symbol

        for (int i = 0; i < Constants.Game.CELL_HEIGHT; i++) {
            for (int j = 0; j < Constants.Game.CELL_WIDTH; j++) {
                stringBuilder.append(' ');
            }
            stringBuilder.append('\n');
        }

        return stringBuilder;
    }

    public static void putDataToModel(StringBuilder position1, StringBuilder position2, Model model) {
        String position1Str = position1.toString();
        String position2Str = position2.toString();
        List<Integer> coordinatesX = new ArrayList<>(Constants.Game.CELL_WIDTH);
        List<Integer> coordinatesY = new ArrayList<>(Constants.Game.CELL_HEIGHT);

        for (int i = 0; i < Constants.Game.CELL_WIDTH; i++) {
            coordinatesX.add(i);
        }
        for (int i = 0; i < Constants.Game.CELL_HEIGHT; i++) {
            coordinatesY.add(i);
        }

        model.addAttribute("position1Str", position1Str);
        model.addAttribute("position2Str", position2Str);
        model.addAttribute("coordinatesX", coordinatesX);
        model.addAttribute("coordinatesY", coordinatesY);
    }

    public static void formatPosition(StringBuilder position) {
        for (int i = 0; i < position.length(); i++) {
            if (position.charAt(i) == '\r') {
                position.deleteCharAt(i);
            }
        }
    }
}
