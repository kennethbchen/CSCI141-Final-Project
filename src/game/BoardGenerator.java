package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import entities.*;

public class BoardGenerator {

    private static final String ROOT_ROOM_PATH = "rooms";

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

        state.addEntity(state.getPlayer(), 1, 1);
        state.addEntity(new Wall(), 2, 2);
        state.addEntity(new Wall(), 3, 2);
        state.addEntity(new Slime(), 3, 3);
        
    }

    public static void generateFloor(GameState state) {
        try {
            for(Entity[] line: readRoom(BoardGenerator.class.getResource(ROOT_ROOM_PATH + "/testRoom.txt").toString())){
                for(Entity thing: line) {
                    System.out.println(thing.getClass().getName());
                }
            }
        } catch (IOException e) {
            System.out.println("Error occured while generating floor");
            System.out.println(e);
        }
        
    }

    private static Entity[][] readRoom(String path) throws IOException {
        Entity[][] output = new Entity[ROOM_SIZE][ROOM_SIZE];

        File room = new File(path);
        Scanner reader = new Scanner(new FileReader(room));;

        for(int lineCount = 0; reader.hasNextLine(); lineCount++) {
            char[] line = reader.nextLine().toCharArray();
            for(int i = 0; i < line.length; i++) {
                output[lineCount][i] = charToEntity(line[i]);
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
