package entities;

import java.io.*;
import javax.imageio.ImageIO;

import game.GameState;

public class Potion extends Entity implements Interactable {

    private GameState state;
    
    public Potion(GameState state) {
        this.state = state;
    }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/res/potion.png"));
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    @Override
    public void interact(Player player) {
        player.heal(3);
        state.removeEntity(this);
    }
}
