package entities;

public abstract class Creature extends Entity {
    
    //Stats
    private int health;
    private int maxHealth;

    private int attack;
    private int defense;

    public Creature(){
        super();
    }
    public int getHealth() {
        return health;
    }

    protected void setHealth(int newHealth) {
        if(newHealth >= 0 && newHealth <= maxHealth) {
            health = newHealth;
        }
    }

    public void heal(int healAmount) {
        if(healAmount >= 1) {
            // Prevents healing past maxHealth
            health = Math.min(maxHealth, health + healAmount);
        }
    }

    // Incorperates Defense stat into damage calculation
    public void takeDamage(int damageAmount) {
        if(damageAmount >= 1) {
            // Inner Math.max for if defense is higher than damage ammount. prevents negative damage taken.
            // If defense is greater than damageAmount, then 1 damage is taken
            // Outer Math.max for if damage taken is less than 0. Prevents negative health.
            health = Math.max(0, health - Math.max(1, damageAmount - defense));


        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isDead() {
        if(health <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setMaxHealth(int maxHealth) {
        if(maxHealth >= 1) {
            this.maxHealth = maxHealth;
        }
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        if(attack >= 1) {
            this.attack = attack;
        }
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        if(defense >= 1) {
            this.defense = defense;
        }
    }

}
