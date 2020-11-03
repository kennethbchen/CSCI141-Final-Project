package game;

import java.util.ArrayList;

import entities.*;

public class BoardGenerator {

    // Test board with stuff in it to test mechanics
    public static void generateTestBoard(GameState state) {
        //Generate a temp board for testing

        state.addEntity(state.getPlayer(), 1, 1);
        state.addEntity(new Wall(), 2, 2);
        state.addEntity(new Wall(), 3, 2);
        state.addEntity(new Slime(), 3, 3);
        
    }

}
