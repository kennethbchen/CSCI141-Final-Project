package game;

import entities.*;

public class BoardGenerator {

    public static Entity[][] generateBoard(Player player) {
        Entity[][] output = new Entity[16][16];
        output[1][1] = player;
        return output;
    }

}
