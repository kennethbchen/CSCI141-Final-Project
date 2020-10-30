package game;

import entities.*;

public class GameState {
    
    private int boardLength;
    private int boardHeight;

    // gameBoard[0,0] is world coordinates (0,0)
    private Entity[][] gameBoard;  
    
    private Player player;

    public GameState(int boardLength, int boardHeight) {
        this.boardLength = boardLength;
        this.boardHeight = boardHeight;

        player = new Player();

        gameBoard = BoardGenerator.generateBoard(player);

        

    }

    public GameState() {
        this(16,16);
    }

    protected Entity[][] getBoard() {
        return gameBoard;
    }

    protected Player getPlayer() {
        return player;
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

    public boolean inBoard(int x, int y) {
        if(x <= boardLength - 1 && x >= 0 && 
                y <= boardHeight - 1 && y >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void takeTurn() {
        for(int x = 0; x < gameBoard.length; x++) {
            for(int y = 0; y < gameBoard[x].length; y++) {
                // Call move() On every Creature that is not the player
                if(gameBoard[x][y] instanceof AICreature) {
                    AICreature thing = (AICreature) gameBoard[x][y];
                    thing.move(this);
                }
            }
        }   
    }

}
