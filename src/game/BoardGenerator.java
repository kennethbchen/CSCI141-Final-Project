package game;

import entities.*;

public class BoardGenerator {

    // Test board with stuff in it to test mechanics
    public static Entity[][] generateTestBoard(Player player, int length, int height) {
        //Generate a temp board for testing
        Entity[][] output = new Entity[length][height];
        output[1][1] = player;

        output[2][2] = new Wall();
        
        player.setPosition(1, 1);
        return output;
    }

}
