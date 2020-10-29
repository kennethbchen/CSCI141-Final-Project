package entities;

import java.awt.image.BufferedImage;

public abstract class Entity {
    
    private int xPos;
    private int yPos;
    
    BufferedImage sprite;

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public abstract void loadSprite();

    public BufferedImage getSprite() {
        return sprite;
    }
}
