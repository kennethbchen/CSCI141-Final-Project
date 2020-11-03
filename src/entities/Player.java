package entities;

import java.io.*;

import javax.imageio.ImageIO;


import game.GameState;

public class Player extends Creature {

    private int keys;

    public Player() {
        // TODO Init Stats        
        loadSprite();
    }

    public int getKeys() {
        return keys;
    }

    public void addKey() {
        keys++;
    }

    public void subKey() {
        if (keys > 0) {
            keys--;
        }
    }

    public void move(GameState state, Direction direction) {
        // This may cause problems if both dx and dy are both 0 somehow. 
        // If the player starts atticking themselves, this is why
        int dx = 0;
        int dy = 0; 
        

        // Determine where the player is going to move
        if(direction == Direction.UP && state.inBoard(getXPos(), getYPos() - 1)) {
            dy--;
        } else if(direction == Direction.DOWN && state.inBoard(getXPos(), getYPos() + 1)) {
            dy++;
        } else if(direction == Direction.LEFT && state.inBoard(getXPos() - 1, getYPos())) {
            dx--;
        } else if(direction == Direction.RIGHT && state.inBoard(getXPos() + 1, getYPos())) {
            dx++;
        }

        // Check if the move is in the board
        if(state.inBoard(getXPos() + dx, getYPos() + dy)) {

            // Check what is occupying where the player wants to move
            if(state.getAtPos(getXPos() + dx, getYPos() + dy) == null) {
                // If there's empty space where the player wants to move, move there
                setXPos(getXPos() + dx);
                setYPos(getYPos() + dy);
            } else if (state.getAtPos(getXPos() + dx, getYPos() + dy) instanceof Creature) {
                // If there's a creature in the way, attack it
            } // Other checks for moving into objects like keys go here
        }
    
    }

    @Override
    public void attack(Creature target) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadSprite() {
        
        try {
        sprite = ImageIO.read(new File("src/res/Player.png"));;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}
