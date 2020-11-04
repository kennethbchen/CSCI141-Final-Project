package game;

import java.io.File;

import entities.*;

public class BoardGenerator {

    private final int ROOM_SIZE = 8; // nxn room

    private final char EMPTY = '.';
    private final char WALL = '+';
    private final char PLAYER = 'p';
    private final char DOOR = '8';
    
    // Test floor with stuff in it to test mechanics
    public static void generateTestFloor(GameState state) {

        state.addEntity(state.getPlayer(), 1, 1);
        state.addEntity(new Wall(), 2, 2);
        state.addEntity(new Wall(), 3, 2);
        state.addEntity(new Slime(), 3, 3);
        
    }

    public static void generateFloor(GameState state) {

    }

    private Entity[][] readRoom(String path) {
        Entity[][] output = new Entity[ROOM_SIZE][ROOM_SIZE];

        File room = new File(path);
        
        return output;
    }

    private Entity charToEntity(char c, Player p) {
        switch(c) {
            case EMPTY:
                return null;
            case WALL:
                return new Wall();
            case PLAYER:
                return p;
            default:
                return null;
        }

    }

}
