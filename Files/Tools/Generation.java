package Files.Tools;

import java.util.Random;

public class Generation {
    private char[][] map;
    private final int dimensions = 20;
    private static final Random rand = new Random();

    public Generation() {
        // Constructor
    }

    public char[][] TileSet() {
        map = new char[dimensions][dimensions];
        map = InitializeEmptyMap(map);

        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                char cell = map[i][j];

                if (cell != '*' && i != 0 && i != dimensions - 1 && j != 0 && j != dimensions - 1) {
                    if (rand.nextInt(5) == 0) {
                        map[i][j] = '*';
                    }
                }
            }
        }
        return map;
    }

    private char[][] InitializeEmptyMap(char[][] map) {
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (i == 0 || i == dimensions - 1 || j == 0 || j == dimensions - 1) {
                    map[i][j] = '*';
                } else {
                    map[i][j] = ' ';
                }
            }
        }
        return map;
    }
}
