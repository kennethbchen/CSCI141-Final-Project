package game;

import entities.*;

public class GameState {
    
    private int boardLength;
    private int boardHeight;

    // gameBoard[0,0] is world coordinates (0,0)
    private Entity[][] gameBoard;

    public GameState(int boardLength, int boardHeight) {
        this.boardLength = boardLength;
        this.boardHeight = boardHeight;

        gameBoard = new Entity[boardLength][boardHeight];
        // for(Entity[] col: gameBoard) {
        //     for(Entity thing: col) {
        //         thing = null;
        //     }
        // }
    }

    public GameState() {
        this(5,5);
    }

    // 0-Indexed
    public Entity getAtPos(int x, int y) {
        // Check if the inputs are in bounds
        if(x <= boardLength - 1 && y <= boardHeight - 1) {
            return gameBoard[x][y];
        } else {
            return null;
        }
    }

    public void takeTurn() {
        for(int x = 0; x < gameBoard.length; x++) {
            for(int y = 0; y < gameBoard[x].length; y++) {
                // Call move() On every Creature that is not the player
                if(gameBoard[x][y] instanceof Creature && 
                !(gameBoard[x][y] instanceof Player) ) {
                    Creature thing = (Creature) gameBoard[x][y];
                    thing.move(this);
                }
            }
        }   
    }

    public void renderBoard() {

    }
}
