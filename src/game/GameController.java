package game;

import entities.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameController extends JPanel {

    private final int RENDER_GRID_SIZE = 15;
    private final int SPRITE_SIZE = 16;

    GameState state;

    private boolean inGame = false;
    private boolean playerLost = false;

    JLabel healthValue;
    JLabel attackValue;
    JLabel defenseValue;
    JLabel keyValue;
    JLabel floorValue;

    public GameController(JLabel healthValue, JLabel attackValue, JLabel defenseValue,
        JLabel keyValue, JLabel floorValue) {
        this.healthValue = healthValue;
        this.attackValue = attackValue;
        this.defenseValue = defenseValue;
        this.keyValue = keyValue;
        this.floorValue = floorValue;

        addKeyListener(new Listener());
    }

    public void startGame() {
        state = new GameState();
        inGame = true;
        playerLost = false;
        repaint();
    }

    public void loseGame() {
        playerLost = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If the game is started
        if (inGame) {
            // startGame() Must have been called at some point before this
            drawGame(g);
        } else {
            // Else draw the title screen
            drawTitle(g);
        }

    }

    private void drawTitle(Graphics g) {
        // General Graphics2D Boilerplate from http://zetcode.com/javagames/basics/
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);

        Dimension size = getSize();
        double width = size.getWidth();
        double height = size.getHeight();

        graphics.drawString("Title Screen", (int)(width/2), (int)(height/2));
    }

    private void drawGame(Graphics g) {
        // General Graphics2D Boilerplate from http://zetcode.com/javagames/basics/
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);
        
        Dimension size = getSize();
        double screenWidth = size.getWidth();
        double screenHeight = size.getHeight();

        // Box lengths for grid is always a proportion of height and grid cell size
        int boxLength = (int)( screenHeight / RENDER_GRID_SIZE);
        int padding = (int) ((screenWidth - (boxLength * RENDER_GRID_SIZE)) / 2);

        // Origins in game state space
        int renderOriginX = state.getPlayer().getXPos() - RENDER_GRID_SIZE / 2;
        int renderOriginY = state.getPlayer().getYPos() - RENDER_GRID_SIZE / 2;

        // Loops through game state space
        // rows and columns are game state space (array indexes)
        // row/column - renderOriginX/Y are screen space coordinates
        for(int column = renderOriginX; column < renderOriginX + RENDER_GRID_SIZE; column++) {
            
            for(int row = renderOriginY; row < renderOriginY + RENDER_GRID_SIZE; row++) {
                
                graphics.setColor(Color.GRAY);
                // Draws in screen space
                if (state.inBoard(column, row)) {
                    // Inside the board, draw grid
                    graphics.setBackground(Color.WHITE);
                    graphics.drawRect(( (column - renderOriginX) * boxLength) + padding, (row - renderOriginY) * boxLength, boxLength, boxLength);

                    // If the index (column,row) has something in it (Player, wall, enemy, whatever)
                    // Draw a scaled image
                    // The grid is drawn centered on the player
                    if(state.getAtPos(column, row) != null) {
                        
                        // Image Scaling from https://blog.idrsolutions.com/2019/05/image-scaling-in-java/
                        BufferedImage scaled = new BufferedImage(boxLength, boxLength, BufferedImage.TYPE_INT_ARGB);
                        final AffineTransform transform = AffineTransform.getScaleInstance( (double) boxLength / SPRITE_SIZE, (double) boxLength / SPRITE_SIZE);
                        final AffineTransformOp ato = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                        scaled = ato.filter(state.getBoard()[column][row].getSprite(), scaled);
    
                        graphics.drawImage(scaled, ((column - renderOriginX) * boxLength) + padding, (row - renderOriginY) * boxLength, this);

                        // Rendering health bar
                        if(state.getAtPos(column, row) instanceof Creature) {
                            Creature thing = (Creature) state.getAtPos(column, row);
                            if(thing.getHealth() != thing.getMaxHealth()) {
                                // Render health function here, Scale independant
                            }
                        }
                    }

                } else {
                    // Outside the board, fill gray like the walls
                    graphics.setBackground(Color.GRAY);
                    // Add 1 to the length of the gray rectangles because they were too small?
                    graphics.fillRect(((column - renderOriginX) * boxLength) + padding, (row - renderOriginY) * boxLength, boxLength + 1, boxLength + 1);
                }

                
            }
        }

        // Update the UI with new player stats
        updateInterface();
    }

    private void renderHealth(Creature creature) {

    }

    private void updateInterface() {
        healthValue.setText(state.getPlayer().getHealth() + "/" + state.getPlayer().getMaxHealth());
        attackValue.setText(String.valueOf(state.getPlayer().getAttack()));
        defenseValue.setText(String.valueOf(state.getPlayer().getDefense()));
        keyValue.setText(String.valueOf(state.getPlayer().getKeys()));
        floorValue.setText(String.valueOf(state.getFloors()));
    }

    private class Listener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            
            // Game inputs
            if(inGame && !playerLost) {
                int prevX = state.getPlayer().getXPos();
                int prevY = state.getPlayer().getYPos();

                if (e.getKeyCode() == KeyEvent.VK_UP || 
                        e.getKeyCode() == KeyEvent.VK_DOWN || 
                            e.getKeyCode() == KeyEvent.VK_LEFT ||
                                e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                    
                    switch (e.getKeyCode()) {
                        case(KeyEvent.VK_UP):
                            state.getPlayer().move(state, Direction.UP);
                            break;
                        case(KeyEvent.VK_DOWN):
                            state.getPlayer().move(state, Direction.DOWN);
                            break;
                        case(KeyEvent.VK_LEFT):
                            state.getPlayer().move(state, Direction.LEFT);
                            break;
                        case(KeyEvent.VK_RIGHT):
                            state.getPlayer().move(state, Direction.RIGHT);
                            break;    
                    }

                    // Update position on the board
                    state.updateEntityPosition(state.getPlayer(), prevX, prevY);
                    state.takeTurn();

                    if(state.getPlayer().getHealth() == 0) {
                        // If player is dead, end the game
                        loseGame();
                    }
                    repaint();
                
                }
                
            } else if (playerLost) {
                startGame();
            }
            else {
                // Title Screen inputs
                startGame();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {}

    }



    

}
