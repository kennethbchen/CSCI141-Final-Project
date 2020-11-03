package entities;

import java.io.*;
import javax.imageio.ImageIO;

public class Wall extends Entity {

    public Wall() {
        loadSprite();
    }
    
    @Override
    public void loadSprite() {

        try {
            sprite = ImageIO.read(new File("src/res/Wall.png"));;
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    
}
