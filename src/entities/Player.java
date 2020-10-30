package entities;

import java.io.*;

import javax.imageio.ImageIO;


import game.GameState;

public class Player extends Creature {

    private int keys;

    public Player() {
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
        // Temp does not check for other entities
        if(direction == Direction.UP && state.inBoard(getXPos(), getYPos() + 1)) {
            System.out.println("Moved Up");
            setYPos(getYPos() + 1);
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
