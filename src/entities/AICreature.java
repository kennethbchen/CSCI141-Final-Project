package entities;

import game.GameState;

public abstract class AICreature extends Creature {
    
    abstract public void move(GameState state);
}
