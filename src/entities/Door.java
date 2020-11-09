package entities;

import java.io.*;
import javax.imageio.ImageIO;

import game.GameState;

public class Door extends Entity implements Interactable{
    
    private GameState state;
    
    public Door(GameState state) {
        this.state = state;
    }
    
    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/res/door.png"));
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    @Override
    public void interact(Player player) {
        if(player.getKeys() > 0) {
            player.subKey();
            state.removeEntity(this);
        }

    }

}
