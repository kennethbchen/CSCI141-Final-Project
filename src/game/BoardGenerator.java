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
        // The board is the whole map. It consists of rooms
        // The layout determines where the rooms are in the board and what type they are
        char[][] layout = LayoutGenerator.generateLayout();
        
        state.setBoard(new Entity[layout.length * ROOM_SIZE][layout[0].length * ROOM_SIZE]);

        
        // Read the layout and convert that into rooms in the board
        for(int layoutColumn = 0; layoutColumn < LayoutGenerator.MAX_LAYOUT_SIZE; layoutColumn++) {
            for(int layoutRow = 0; layoutRow < LayoutGenerator.MAX_LAYOUT_SIZE; layoutRow++) {
                if(layout[layoutColumn][layoutRow] == LayoutGenerator.START) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, "start.txt");
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.NORMAL) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, "normal.txt");
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.FINISH) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, "finish.txt");

                }
            }
        }

    }

    private static String pickRoom(char type) {
        // Randomly select a room of the given type
        return "t";
    }

    

    // Loads a room into the board starting at (layoutX, layoutY), the top left part of the room
    private static void putRoomInBoard(GameState state, char[][] layout, int layoutX, int layoutY, String roomName) {
        try {
            Entity[][] room = readRoom(ROOT_ROOM_PATH + roomName);
            // Convert layout space to board space
            int boardX = layoutToBoard(layoutX);
            int boardY = layoutToBoard(layoutY);

                for(int column = boardX; column < boardX + ROOM_SIZE; column++) {
                    for(int row = boardY; row < boardY + ROOM_SIZE; row++) {
                        state.getBoard()[column][row] = room[column - boardX][row - boardY];
                        if(state.getBoard()[column][row] instanceof Player) {
                            state.getPlayer().setPosition(column, row);
                        }
                        // Make holes for the sides of rooms that are adjacent to other rooms
                        // Solution does not work with arbitrary room sizes. Only works when Room size = 8x8
                        if(LayoutGenerator.inLayout(layoutX - 1, layoutY) && layout[layoutX - 1][layoutY] != LayoutGenerator.NULL_CHAR) {
                            // Left
                            state.getBoard()[boardX][boardY + 3] = null;
                            state.getBoard()[boardX][boardY + 4] = null;
                        }
                        if(LayoutGenerator.inLayout(layoutX + 1, layoutY) && layout[layoutX + 1][layoutY] != LayoutGenerator.NULL_CHAR) {
                            // Right
                            state.getBoard()[boardX + 7][boardY + 3] = null;
                            state.getBoard()[boardX + 7][boardY + 4] = null;
                        }
                        if(LayoutGenerator.inLayout(layoutX, layoutY - 1) && layout[layoutX][layoutY - 1] != LayoutGenerator.NULL_CHAR) {
                            // Up
                            state.getBoard()[boardX + 3][boardY] = null;
                            state.getBoard()[boardX + 4][boardY] = null;
                        }
                        if(LayoutGenerator.inLayout(layoutX, layoutY + 1) && layout[layoutX][layoutY + 1] != LayoutGenerator.NULL_CHAR) {
                            // Down
                            state.getBoard()[boardX + 3][boardY + 7] = null;
                            state.getBoard()[boardX + 4][boardY + 7] = null;
                        }

                    
                    }
                }
        } catch (IOException e) {
            System.out.println("Error occured while generating floor");
            System.out.println(e);
        }
    }

    // Converts layout coordinates to board coordinates
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
