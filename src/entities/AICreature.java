package entities;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

import game.GameState;

public abstract class AICreature extends Creature {
    
    
    private final int sightRange = 7;

    GameState state;

    // int[] is [x,y] points
    ArrayDeque<int[]> moveQueue;
    int[] lastPlayerPosition;

    public AICreature(GameState state) {
        super();
        moveQueue = new ArrayDeque<int[]>();
        this.state = state;
    }

    public void move(GameState state) {
        ArrayList<int[]> lineOfSight = getLineToPlayer();

        if(hasLineOfSight(lineOfSight)) {
            // Add all points in the line of sight to the move queue
            moveQueue = new ArrayDeque<int[]>();
            for(int[] point: lineOfSight) {
                moveQueue.add(point);
            }
        }


        if(moveQueue.size() > 0) {

            // If the next point is diagonal (both x and y value changes)
            boolean diagonal = getXPos() - moveQueue.peekFirst()[0] != 0 && getYPos() - moveQueue.peekFirst()[1] != 0;
            if( diagonal && Math.abs(state.getPlayer().getXPos() - getXPos()) <= 1 &&
                    Math.abs(state.getPlayer().getYPos() - getYPos()) <= 1) {

                if(lastPlayerPosition != null) {
                    moveQueue.addFirst(new int[] {lastPlayerPosition[0], lastPlayerPosition[1]});
                }


            }
            if(diagonal) {
                //add a move to the beginning of the queue that is cardinal

                // Try to move horizontally to fix the diagonal
                // If that's not a valid move, move vertically
                if(state.isEmptySpace(moveQueue.peekFirst()[0], getYPos())) {
                    moveQueue.addFirst(new int[] {moveQueue.peekFirst()[0], getYPos()});
                } else if (state.isEmptySpace(getXPos(), moveQueue.peekFirst()[1])) {
                    moveQueue.addFirst(new int[] {getXPos(), moveQueue.peekFirst()[1]});
                }
                    
            }


            // Get the next point to move to in the grid and move there
            int[] point = moveQueue.pollFirst();
            
            if(state.isEmptySpace(point[0], point[1])) {
                setXPos(point[0]);
                setYPos(point[1]);
            } else if (state.getAtPos(point[0], point[1]) instanceof Player) {
                Player p = (Player) state.getAtPos(point[0], point[1]);
                p.takeDamage(getAttack());

            }

            lastPlayerPosition = new int[] {state.getPlayer().getXPos(), state.getPlayer().getYPos()};
        }
        

        
    
    }
    // Gives line of sight to player, goes through walls
    private ArrayList<int[]> getLineToPlayer() {
        // Bresenham's Line Algorithm from
        // http://www.roguebasin.com/index.php?title=Bresenham%27s_Line_Algorithm
        int x0 = getXPos();
        int y0 = getYPos();
        int x1 = state.getPlayer().getXPos();
        int y1 = state.getPlayer().getYPos();

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        boolean swapped = false;

        if(steep) {
            // If it is steep, then swap x and y values
            int temp = x0;
            x0 = y0;
            y0 = temp;

            temp = x1;
            x1 = y1;
            y1 = temp;
        }
        // If the initial point is larger than the desination point 
        // (i.e the initial point is to the right of the destination point)
        // Swap the initial and destination points
        // This makes x0 always less than x1 and dx always negative
        if(x0 > x1) {
            swapped = true;
            int temp = x0;
            x0 = x1;
            x1 = temp;

            temp = y0;
            y0 = y1;
            y1 = temp;
        }

        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);
        int err = dx / 2;
        int ystep = y0 < y1 ? 1 : -1; // Determines whether the line goes up or down
        int y = y0;

        ArrayList<int[]> lineOfSight = new ArrayList<int[]>();

        for (int x = x0; x <= x1; ++x) {

            // Record every point in the line between the creature and the player
            if(steep) {
                lineOfSight.add(new int[] {y, x});
            } else {
                lineOfSight.add(new int[] {x, y});
            }
            err = err - dy;
            if(err < 0) {
                y += ystep;
                err += dx;
            }
        }

        // If we swapped p0 and p1, the first value would be from the player
        // Swap so we iterate from the creature to the player
        if(swapped) {
            Collections.reverse(lineOfSight);
        }
        lineOfSight.remove(0);
        
        return lineOfSight;
    }

    private boolean hasLineOfSight(ArrayList<int[]> lineOfSight) {
        if(lineOfSight.size() > sightRange) {
            return false;
        }
        for(int[] point: lineOfSight) {
            if( state.getAtPos(point[0], point[1]) != null && 
                !(state.getAtPos(point[0], point[1]) instanceof Player) &&
                    (state.getAtPos(point[0], point[1]) != this)  ) {
                // Something is blocking the way
                return false;
            }
        }

        return true;
    }
}
