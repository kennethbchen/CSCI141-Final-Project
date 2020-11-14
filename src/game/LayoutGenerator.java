package game;

import java.util.ArrayList;
import java.util.Random;

public class LayoutGenerator {


    // Layout Constants

    public static final int MAX_LAYOUT_SIZE = 6; // Max layout is nxn grid of rooms
    
    private static final int MIN_ROOMS = 8;
    private static final int MIN_TREASURE_ROOMS = 2;
    private static final int MIN_NORMAL_ROOMS = 2;
    private static final int MIN_ENEMY_ROOMS = 2;

    // All three have to add to 1
    private static final double TREASURE_ROOM_CHANCE = .10;
    private static final double ENEMY_ROOM_CHANCE = .40;
    private static final double DOOR_ROOM_CHANCE = .20;
    // Normal room is just not the other rooms
    

    
    public static final char NULL_CHAR = '\u0000';
    public static final char START = 's';
    public static final char FINISH = 'f';
    public static final char TREASURE = 't';
    public static final char NORMAL = 'n';
    public static final char ENEMY = 'e';
    public static final char DOOR = 'd';
    public static final char KEY = 'k';


    // A layout is a configuration of rooms.
    public static char[][] generateLayout() {
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
        // Layout generation concept (implementation by me) from https://github.com/Miziziziz/ThineCometh
        for(int roomsPlaced = 1; roomsPlaced <= maxRooms; roomsPlaced++) {
            int randIndex = rand.nextInt(possibleSpaces.size()); // (0, size]
            int[] point = possibleSpaces.remove(randIndex);
            
            if(roomsPlaced >= maxRooms) {
                // Final room, place the final room
                layout[point[0]][point[1]] = 'f';
            } else {
                // Choose what room type to make the spot.
                // Currently does not take into account maximum room type numbers

                if(doorRooms > keyRooms) {
                    // More Door rooms than key rooms, make the next room a key room
                    layout[point[0]][point[1]] = KEY;
                    keyRooms++;
                    continue;
                }
                


                double randomNumber = rand.nextDouble(); // (0,1]

                if(randomNumber < TREASURE_ROOM_CHANCE) { // (0, .10]
                    layout[point[0]][point[1]] = TREASURE;
                } else if ( randomNumber > TREASURE_ROOM_CHANCE &&
                        randomNumber <= TREASURE_ROOM_CHANCE + ENEMY_ROOM_CHANCE) { // (.10, .5]
                    layout[point[0]][point[1]] = ENEMY;
                } else if ( roomsPlaced < 8 && randomNumber > TREASURE_ROOM_CHANCE + ENEMY_ROOM_CHANCE &&
                    randomNumber <= TREASURE_ROOM_CHANCE + ENEMY_ROOM_CHANCE + DOOR_ROOM_CHANCE) { // (.5, .70]
                    layout[point[0]][point[1]] = DOOR;
                    doorRooms++;
                } else { // (.25, 1)
                    layout[point[0]][point[1]] = NORMAL;
                }

                
            }
            
            addAdjacentRooms(layout, possibleSpaces, point[0], point[1]); 
        }

        // Print the layout for debugging
        // Printing line by line, so row first, then column
        for(int row = 0; row < MAX_LAYOUT_SIZE; row++) {
            for(int column = 0; column < MAX_LAYOUT_SIZE; column++) {
                if(layout[column][row] != NULL_CHAR) {
                    
                    System.out.print(layout[column][row]);
                } else {
                    System.out.print("+");
                }
            }
            System.out.println();
        }
        System.out.println("--------");

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

    public static boolean inLayout(int x, int y) {
        if(x < MAX_LAYOUT_SIZE && x >= 0 &&
            y < MAX_LAYOUT_SIZE && y >= 0 ){
                return true;
        } else {
            return false;
        }
    }
}
