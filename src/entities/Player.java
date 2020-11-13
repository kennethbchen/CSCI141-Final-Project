package entities;

import java.io.File;
import javax.imageio.ImageIO;


import game.GameState;
import entities.Interactable;

public class Player extends Creature {

    private int keys;

    public Player() {
        // Default Stats
        setMaxHealth(10);
        heal(10);
        setAttack(1);
        setDefense(0);
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
            } else if (state.getAtPos(getXPos() + dx, getYPos() + dy) instanceof Creature &&
                            !(state.getAtPos(getXPos() + dx, getYPos() + dy) instanceof Player)) {
                Creature target = (Creature) state.getAtPos(getXPos() + dx, getYPos() + dy);
                target.takeDamage(getAttack());
            } else if (state.getAtPos(getXPos() + dx, getYPos() + dy) instanceof Interactable) {
                // Checks for things like keys, doors, stairs, and items
                Interactable thing = (Interactable) state.getAtPos(getXPos() + dx, getYPos() + dy);
                thing.interact(this);
            }
                
        }
    
    }

    @Override
    public void loadSprite() {
        
        try {
        sprite = ImageIO.read(new File("src/res/player.png"));;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}
