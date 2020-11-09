package entities;

import java.io.File;
import javax.imageio.ImageIO;

import game.GameState;

public class Stairs extends Entity implements Interactable{

    private GameState state;
    
    public Stairs(GameState state) {
        this.state = state;
    }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/res/stairs.png"));
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    @Override
    public void interact(Player player) {
        state.newFloor();
    }
    
}
