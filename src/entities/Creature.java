package entities;

public abstract class Creature extends Entity {
    
    //Stats
    private int health;
    private int maxHealth;

    private int attack;
    private int defense;

    public int getHealth() {
        return health;
    }

    public void heal(int healAmount) {
        if(healAmount >= 1) {
            health = Math.min(maxHealth, health + healAmount);
        }
    }

    public void takeDamage(int damageAmount) {
        if(damageAmount >= 1) {
            
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    abstract public void move();
    abstract public void attack(Creature target);

}
