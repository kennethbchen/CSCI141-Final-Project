package game;

import entities.*;

public class BoardGenerator {

    public static Entity[][] generateBoard(Player player) {
        //Generate a temp board for testing
        Entity[][] output = new Entity[16][16];
        output[1][1] = player;

        output[2][2] = new Wall();
        
        player.setPosition(1, 1);
        return output;
    }

}
