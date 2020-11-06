package game;

import java.util.Random;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import entities.*;

public class BoardGenerator {

    // Room Constants

    private static final String ROOT_ROOM_PATH = "src/rooms/";

    private static final int ROOM_SIZE = 8; // nxn room

    private static final char EMPTY = '.';
    private static final char WALL = '+';
    private static final char PLAYER = 'p';
    private static final char SLIME = '1';
    private static final char DOOR = '8';

    // Layout Constants

    private static final int MAX_LAYOUT_SIZE = 6; // Max layout is nxn grid of rooms
    private static final char START = 's';
    private static final char FINISH = 'f';
    private static final char TREASURE = 't';
    private static final char NORMAL = 'n';
    private static final char ENEMY = 'e';
    
    private static Player player;

    public static void setPlayer(Player p) {
        player = p;
    }

    // Test floor with stuff in it to test mechanics
    public static void generateTestFloor(GameState state) {

        state.addEntity(state.getPlayer(), 1, 1);
        state.addEntity(new Wall(), 1, 5);
        state.addEntity(new Wall(), 2, 2);
        state.addEntity(new Wall(), 3, 2);
        state.addEntity(new Slime(), 3, 3);
        
    }

    public static void generateFloor(GameState state) {
        char[][] layout = generateLayout(state);
        state.setBoard(new Entity[layout.length * ROOM_SIZE][layout[0].length * ROOM_SIZE]);

        try {
            Entity[][] room = readRoom(ROOT_ROOM_PATH + "testRoom.txt");
            int originX = 0;
            int originY = 0;
            for(int column = originX; column < originX + ROOM_SIZE; column++) {
                for(int row = originY; row < originY + ROOM_SIZE; row++) {
                    state.getBoard()[column][row] = room[column - originX][row - originY];
                    if(state.getBoard()[column][row] instanceof Player) {
                        state.getPlayer().setPosition(column, row);
                    }
                    
                    
                }
            }
            
            
        } catch (IOException e) {
            System.out.println("Error occured while generating floor");
            System.out.println(e);
        }
        
    }

    // A layout is a configuration of rooms.
    private static char[][] generateLayout(GameState state) {
        int maxRooms = 10;
        int rooms = 0;
        int keyRooms = 0;
        int doorRooms = 0;
        
        Random rand = new Random();
        
        char[][] layout = new char[MAX_LAYOUT_SIZE][MAX_LAYOUT_SIZE];
        // Select one point from the middle square of the layout to be the start
        layout[rand.nextInt(MAX_LAYOUT_SIZE - 4) + 2][rand.nextInt(MAX_LAYOUT_SIZE - 4) + 2] = 's';


        // The last room to be chosen is the finish
        return layout;
    }

    private static int layoutToBoard(int layoutCoordinate) {
        return ROOM_SIZE * layoutCoordinate;
    }

    // A room is a square of length ROOM_SIZE.
    // Multiple rooms are put together in a layout to make a floor
    private static Entity[][] readRoom(String path) throws IOException {
        Entity[][] output = new Entity[ROOM_SIZE][ROOM_SIZE];

        File room = new File(path);
        Scanner reader = new Scanner(new FileReader(room));;

        for(int lineCount = 0; reader.hasNextLine(); lineCount++) {
            char[] line = reader.nextLine().toCharArray();
            for(int i = 0; i < line.length; i++) {
                // A difference in i means a horizontal difference in the line
                // That's why it's output[i][lineCount] instead of output[linCount][i]
                output[i][lineCount] = charToEntity(line[i]);
            }
        }
 
        reader.close();

        return output;
    }

    private static Entity charToEntity(char c) {
        switch(c) {
            case EMPTY:
                return null;
            case WALL:
                return new Wall();
            case PLAYER:
                return player;
            case SLIME:
                return new Slime();
            default:
                System.out.println("Invalid room element " + c + " returning null");
                return null;
        }

    }


}
