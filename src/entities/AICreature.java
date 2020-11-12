package entities;

import java.util.ArrayList;
import java.util.Collections;

import game.GameState;

public abstract class AICreature extends Creature {
    
    // int[] are [x,y] points
    
    int[] lastPlayerPos;

    GameState state;

    public AICreature(GameState state) {
        super();

        this.state = state;
    }

    public void move(GameState state) {
        //LineDrawing.drawLine(getXPos(), getYPos(), state.getPlayer().getXPos(), state.getPlayer().getYPos(), this);
        if(hasLineOfSight()) {
            System.out.println("has line of sight");
            // Update the last known player position
            lastPlayerPos = new int[] {state.getPlayer().getXPos(), state.getPlayer().getYPos()};
        }

        if(lastPlayerPos != null) {
            // Some pathfinding algorithm goes here
        }
        
    
    }

    private boolean hasLineOfSight() {
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

        for(int[] point: lineOfSight) {
            if(state.getAtPos(point[0], point[1]) instanceof Wall) {
                // A wall is blocking the line of sight
                return false;
            }
        }

        return true;
    }
}
