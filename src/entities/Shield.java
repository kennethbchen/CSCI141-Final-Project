package entities;

import java.io.File;
import javax.imageio.ImageIO;

import game.GameState;

public class Shield extends Entity implements Interactable {
    
    GameState state;

    public Shield(GameState state) {
        this.state = state;
    }
    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/res/shield.png"));
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    @Override
    public void interact(Player player) {
        player.setDefense(player.getDefense() + 1);
        state.removeEntity(this);

    }

}
