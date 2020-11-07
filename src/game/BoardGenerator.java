package game;

import java.util.ArrayList;
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
    private static final char NULL_CHAR = '\u0000';
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
        state.setBoard(new Entity[8][8]);
        state.addEntity(state.getPlayer(), 1, 1);
        state.addEntity(new Wall(), 1, 5);
        state.addEntity(new Wall(), 2, 2);
        state.addEntity(new Wall(), 3, 2);
        state.addEntity(new Slime(), 3, 3);
        
    }

    public static void generateFloor(GameState state) {
        char[][] layout = generateLayout();
        
        state.setBoard(new Entity[layout.length * ROOM_SIZE][layout[0].length * ROOM_SIZE]);

        
        // Read the layout and convert that into rooms in the board
        for(int layoutColumn = 0; layoutColumn < MAX_LAYOUT_SIZE; layoutColumn++) {
            for(int layoutRow = 0; layoutRow < MAX_LAYOUT_SIZE; layoutRow++) {
                if(layout[layoutColumn][layoutRow] == 's') {
                    layoutToRoom(state, layoutToBoard(layoutColumn), layoutToBoard(layoutRow), "start.txt");
                } else if (layout[layoutColumn][layoutRow] == 'n') {
                    layoutToRoom(state, layoutToBoard(layoutColumn), layoutToBoard(layoutRow), "normal.txt");
                }
            }
        }

    }

    // Converts layout coordinates to board coordinates
    private static int layoutToBoard(int layoutCoordinate) {
        return ROOM_SIZE * layoutCoordinate;
    }

    // Loads a room into the board starting at (originX, originY), the top left part of the room
    private static void layoutToRoom(GameState state, int originX, int originY, String roomName) {
        try {
            Entity[][] room = readRoom(ROOT_ROOM_PATH + roomName);

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
    private static char[][] generateLayout() {
        int maxRooms = 10;
        int keyRooms = 0;
        int doorRooms = 0;
        
        Random rand = new Random();
        
        char[][] layout = new char[MAX_LAYOUT_SIZE][MAX_LAYOUT_SIZE];
        

        // Array that holds xy coordinates of possible spaces to put a room
        ArrayList<int[]> possibleSpaces = new ArrayList<int[]>();

        // Select one point from the middle square of the layout to be the start
        int startX = rand.nextInt(MAX_LAYOUT_SIZE - 4) + 2;
        int startY = rand.nextInt(MAX_LAYOUT_SIZE - 4) + 2;
        layout[startX][startY] = 's';

        // Add spaces adjacent to the start point to the possiblRooms list
        addAdjacentRooms(layout, possibleSpaces, startX, startY);

        // Randomly select one of the possible spaces and add appropritte possible spaces
        // Do this until maxRooms have been placed
        for(int roomsPlaced = 0; roomsPlaced < maxRooms; roomsPlaced++) {
            int randIndex = rand.nextInt(possibleSpaces.size()); // (0, size]
            int[] point = possibleSpaces.remove(randIndex);
            layout[point[0]][point[1]] = 'n'; // z for testing
            addAdjacentRooms(layout, possibleSpaces, point[0], point[1]); 
        }

        // Print the layout for debugging
        for(int column = 0; column < MAX_LAYOUT_SIZE; column++) {
            for(int row = 0; row < MAX_LAYOUT_SIZE; row++) {
                if(layout[row][column] != NULL_CHAR) {

                    System.out.print(layout[row][column]);
                } else {
                    System.out.print("+");
                }
            }
            System.out.println();
        }

        // The last room to be chosen is the finish
        return layout;
    }

    private static void addAdjacentRooms(char[][] layout, ArrayList<int[]> possibleSpaces, int x, int y){

        if(availableSpace(layout, x - 1, y) && !possibleSpaces.contains(new int[] {x - 1, y})) {
            // One space left
            possibleSpaces.add(new int[] {x - 1, y});
        }
        if(availableSpace(layout, x + 1, y) && !possibleSpaces.contains(new int[] {x + 1, y})) {
            // One space right
            possibleSpaces.add(new int[] {x + 1, y});
        }
        if(availableSpace(layout, x, y - 1) && !possibleSpaces.contains(new int[] {x, y - 1})) {
            // One space up
            possibleSpaces.add(new int[] {x, y - 1});
        }
        if(availableSpace(layout, x, y + 1) && !possibleSpaces.contains(new int[] {x, y + 1})) {
            // One space down
            possibleSpaces.add(new int[] {x, y + 1});
        }
    }

    private static boolean availableSpace(char[][] layout, int x, int y) {
        if(inLayout(x, y) && layout[x][y] == NULL_CHAR) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean inLayout(int x, int y) {
        if(x < MAX_LAYOUT_SIZE && x >= 0 &&
            y < MAX_LAYOUT_SIZE && y >= 0 ){
                return true;
        } else {
            return false;
        }
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
