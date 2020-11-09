package entities;

import java.io.File;

import javax.imageio.ImageIO;

public class Sword extends Entity implements Interactable {

    

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

    }
    
}
