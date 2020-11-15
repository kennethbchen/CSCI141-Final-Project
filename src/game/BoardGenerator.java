package game;

import java.util.Random;
import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import entities.*;

public class BoardGenerator {

    // Room Constants

    private static final String ROOT_ROOM_PATH = "src/rooms/";
    private static final String START_ROOM_PATH = "start/";
    private static final String FINISH_ROOM_PATH = "finish/";
    private static final String TREASURE_ROOM_PATH = "treasure/";
    private static final String NORMAL_ROOM_PATH = "normal/";
    private static final String ENEMY_ROOM_PATH = "enemy/";
    private static final String DOOR_ROOM_PATH = "door/";
    private static final String KEY_ROOM_PATH = "key/";


    private static final int ROOM_SIZE = 8; // nxn room

    private static final char EMPTY = '.';
    private static final char WALL = '+';
    private static final char PLAYER = 'p';
    private static final char STAIRS = 's';
    private static final char SWORD = 'a';
    private static final char SHIELD = 'd';
    private static final char SLIME = '1';
    private static final char DOOR = '=';
    private static final char KEY = '^';

    
    private static Player player;

    public static void setPlayer(Player p) {
        player = p;
    }

    // Test floor with stuff in it to test mechanics
    public static void generateTestFloor(GameState state) {
        state.setBoard(new Entity[16][16]);
        state.addEntity(state.getPlayer(), 1, 1);
        state.getPlayer().takeDamage(5);
        
        state.addEntity(new Wall(), 2, 2);
        state.addEntity(new Wall(), 3, 2);

        state.addEntity(new Slime(state), 3, 3);
        
        state.addEntity(new Key(state), 4, 4);
        state.addEntity(new Door(state), 5, 4);

        state.addEntity(new Potion(state), 2, 6);
        state.addEntity(new Sword(state), 4, 6);
        state.addEntity(new Shield(state), 5, 6);
        
    }

    public static void generateFloor(GameState state) {
        // The board is the whole map. It consists of rooms
        // The layout determines where the rooms are in the board and what type they are
        char[][] layout = LayoutGenerator.generateLayout();
        
        state.setBoard(new Entity[layout.length * ROOM_SIZE][layout[0].length * ROOM_SIZE]);
        // Fill all spaces with wall entities.
        for(int column = 0; column < state.getBoard().length; column++) {
            for(int row = 0; row < state.getBoard().length; row++) {
                state.getBoard()[column][row] = new Wall();
            }
        }
        
        // Read the layout and convert that into rooms in the board
        for(int layoutColumn = 0; layoutColumn < LayoutGenerator.MAX_LAYOUT_SIZE; layoutColumn++) {
            for(int layoutRow = 0; layoutRow < LayoutGenerator.MAX_LAYOUT_SIZE; layoutRow++) {
                if(layout[layoutColumn][layoutRow] == LayoutGenerator.START) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.START));
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.FINISH) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.FINISH));
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.NORMAL) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.NORMAL));
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.TREASURE) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.TREASURE));
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.ENEMY) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.ENEMY));
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.DOOR) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.DOOR));
                } else if (layout[layoutColumn][layoutRow] == LayoutGenerator.KEY) {
                    putRoomInBoard(state, layout, layoutColumn, layoutRow, pickRoom(LayoutGenerator.KEY));

                }
            }
        }

    }

    private static String pickRoom(char type) {
        // Choose which folder to look into based on the type
        String path = ROOT_ROOM_PATH;
        switch(type) {
            case(LayoutGenerator.START):
            path += START_ROOM_PATH + LayoutGenerator.START;
            break;
            case(LayoutGenerator.FINISH):
            path += FINISH_ROOM_PATH + LayoutGenerator.FINISH;
            break;
            case(LayoutGenerator.TREASURE):
            path += TREASURE_ROOM_PATH + LayoutGenerator.TREASURE;
            break;
            default: // If somehow a weird char is inputted
            case(LayoutGenerator.NORMAL):
            path += NORMAL_ROOM_PATH + LayoutGenerator.NORMAL;
            break;
            case(LayoutGenerator.ENEMY):
            path += ENEMY_ROOM_PATH + LayoutGenerator.ENEMY;
            break;
            case(LayoutGenerator.DOOR):
            path += DOOR_ROOM_PATH + LayoutGenerator.DOOR;
            break;
            case(LayoutGenerator.KEY):
            path += KEY_ROOM_PATH + LayoutGenerator.KEY;
            break;

        }

        // At this point, the path should be "src/rooms/[type]/[type char]"
        // All files start with the char of its type and a number

        int numFiles = new File(path.substring(0, path.length() - 2)).listFiles().length;
        Random rand = new Random();
        path += rand.nextInt(numFiles) + ".txt";

        return path;
    }

    

    // Loads a room into the board starting at (layoutX, layoutY), the top left part of the room
    private static void putRoomInBoard(GameState state, char[][] layout, int layoutX, int layoutY, String roomPath) {
        try {

            File roomFile = new File(roomPath);
            Scanner reader = new Scanner(new FileReader(roomFile));

            // Convert layout space to board space
            int boardX = layoutToBoard(layoutX);
            int boardY = layoutToBoard(layoutY);

                // Loop through rows first because scanner reads rows first
                for(int row = boardY; row < boardY + ROOM_SIZE; row++) {
                        char[] line = reader.nextLine().toCharArray();
                    for(int column = boardX; column < boardX + ROOM_SIZE; column++) {
                        Entity e = null;
                        // Figure out which entity to put in this space, or leave null
                        switch(line[column - boardX]) {

                            case WALL:
                                e = new Wall();
                                break;
                            case PLAYER:
                                e = player;
                                break;
                            case STAIRS:
                                e = new Stairs(state);
                                break;
                            case SWORD:
                                e = new Sword(state);
                                break;
                            case SHIELD:
                                e = new Shield(state);
                                break;
                            case SLIME:
                                e = new Slime(state);           
                                break;
                            case DOOR:
                                e = new Door(state);
                                break;
                            case KEY:
                                e = new Key(state);
                                break;
                            default:
                                System.out.println("Invalid room element " + line[column - boardX] + " returning null");
                            case EMPTY:
                                break;
                        }
                        state.addEntity(e, column, row);

                    }
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

            reader.close();
        } catch (IOException e) {
            System.out.println("Error occured while generating floor");
            System.out.println(e);
        }
    }

    // Converts layout coordinates to board coordinates
    private static int layoutToBoard(int layoutCoordinate) {
        return ROOM_SIZE * layoutCoordinate;
    }




}
