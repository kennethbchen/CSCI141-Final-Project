package entities;

import game.GameState;

public class Player extends Creature {

    private int keys;

    public int getKeys() {
        return keys;
    }

    public void addKey() {
        keys++;
    }

    public void subKey() {
        if(keys > 0) {
            keys--;
        }
    }


    @Override
    public void move(GameState state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attack(Creature target) {
        // TODO Auto-generated method stub

    }
    
}
