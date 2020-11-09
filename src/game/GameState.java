package game;

import java.util.ArrayList;

import entities.*;

public class GameState {
    
    // gameBoard[0,0] is world coordinates (0,0)
    private Entity[][] gameBoard;  

    private int floor;

    private Player player;

    private ArrayList<AICreature> enemies;
    
    // A new instance of GameState means resetting the game
    public GameState() {

        floor = 1;
        player = new Player();
        BoardGenerator.setPlayer(player);

        enemies = new ArrayList<AICreature>();


        // Initialization of the game board is handled by BoardGenerator
        //BoardGenerator.generateTestFloor(this);
        BoardGenerator.generateFloor(this);

    }

    public void newFloor() {
        floor++;
        enemies = new ArrayList<AICreature>();
        BoardGenerator.generateFloor(this);
    }


    protected Entity[][] getBoard() {
        return gameBoard;
    }

    protected int getFloors() {
        return floor;
    }

    protected void setBoard(Entity[][] board) {
        gameBoard = board;
    }

    protected Player getPlayer() {
        return player;
    }

    protected void addEntity(Entity entity, int x, int y) {
        if(inBoard(x, y) && entity != null) {
            entity.setPosition(x, y);
            gameBoard[x][y] = entity;

            if(entity instanceof AICreature) {
                enemies.add((AICreature) entity);
            }
        } else if (entity == null) {
            gameBoard[x][y] = null;
        }
        
    }

    protected void removeEntity(int x, int y) {
        // If it's a creature, remove it from the list of creatures
        if(gameBoard[x][y] instanceof AICreature) {
            // May not work with multiple enemies  of the same type 
            // depending on how .equals() works
            enemies.remove((AICreature) gameBoard[x][y]);
        }
        // Remove it from the board
        gameBoard[x][y] = null;
    }

    public void removeEntity(Entity e) {
        removeEntity(e.getXPos(), e.getYPos());
    }

    // It is assumed that the entity has updated their internal position 
    // accordingly before this method call
    protected void updateEntityPosition(Entity entity, int prevX, int prevY) {
        gameBoard[prevX][prevY] = null;
        gameBoard[entity.getXPos()][entity.getYPos()] = entity;
    }

    // 0-Indexed
    public boolean inBoard(int x, int y) {
        if(x <= gameBoard.length - 1 && x >= 0 && 
                y <= gameBoard[0].length - 1 && y >= 0) {
            return true;
        } else {
            return false;
        }
    }

    // 0-Indexed
    public Entity getAtPos(int x, int y) {
        // Check if the inputs are in bounds
        if(inBoard(x, y)) {
            return gameBoard[x][y];
        } else {
            return null;
        }
    }

    // 0-Indexed
    public boolean isEmptySpace(int x, int y) {
        if(inBoard(x, y) && getAtPos(x, y) == null) {
            return true;
        } else {
            return false;
        }
    }

    
    public void takeTurn() {
        for(int i = 0; i < enemies.size(); i++) {
            // Check if the enemy is dead
            if(enemies.get(i).isDead()) {
                // It's dead, remove it
                removeEntity(enemies.get(i));
            } else {
                // It's alive, let it move
                enemies.get(i).move(this);
            }
            
        }
    }

}
