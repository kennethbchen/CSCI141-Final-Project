package entities;

import java.awt.Image;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

    @Override
    public void move(GameState state) {
        // TODO Auto-generated method stub

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
