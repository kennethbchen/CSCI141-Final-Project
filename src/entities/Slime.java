package entities;

import game.GameState;

import java.io.File;
import javax.imageio.ImageIO;

public class Slime extends AICreature {

    public Slime() {
        setMaxHealth(4);
        heal(4);
        setAttack(1);
        setDefense(0);
    }
    @Override
    public void move(GameState state) {
        // TODO Add AI

    }


    @Override
    public void loadSprite() {
        
        try {
            sprite = ImageIO.read(new File("src/res/slime.png"));;
            } catch (Exception e) {
                System.out.println(e);
            }

    }
    
}
