package entities;

import java.io.*;
import javax.imageio.ImageIO;

import game.GameState;

public class Key extends Entity implements Interactable {

    private GameState state;
    
    public Key(GameState state) {
        this.state = state;
    }
    
    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/res/key.png"));
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    @Override
    public void interact(Player player) {
        player.addKey();
        state.removeEntity(this);

    }
}
