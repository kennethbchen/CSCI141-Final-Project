package entities;

import java.io.File;

import javax.imageio.ImageIO;

import game.GameState;

public class Sword extends Entity implements Interactable {

    GameState state;

    public Sword(GameState state) {
        this.state = state;
    }
    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/res/sword.png"));
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    @Override
    public void interact(Player player) {
        player.setAttack(player.getAttack() + 1);
        state.removeEntity(this);

    }
    
}
