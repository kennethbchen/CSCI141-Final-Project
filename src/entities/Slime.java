package entities;

import game.GameState;

import java.io.File;
import javax.imageio.ImageIO;

public class Slime extends AICreature {

    public Slime(GameState state) {
        super(state);
        int maxHealth = 4 + (int) ( (state.getFloors() - 1) / 3); //Increase Max Health by one every 3 floors
        setMaxHealth(maxHealth);
        heal(getMaxHealth());
        
        int attack = 1 + (int) ( (state.getFloors() - 1) / 2); //Increase Attack by one every 2 floors
        setAttack(attack);

        int defense = 0 + (int) ( (state.getFloors() - 1) / 2); //Increase Defense by one every 2 floors
        setDefense(defense);
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
