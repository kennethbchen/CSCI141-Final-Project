package game;

import entities.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GameController extends JPanel {

    GameState state;

    private boolean inGame = false;

    public GameController() {
        state = new GameState();
        addKeyListener(new Listener());
    }

    public void startGame() {
        inGame = true;
        repaint();
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
        // Boilerplate
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
        // Boilerplate
        Graphics2D graphics = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);
        
        Dimension size = getSize();
        double screenWidth = size.getWidth();
        double screenHeight = size.getHeight();

        int boxLength = (int)((screenHeight) / state.getBoard().length);
        int padding = (int) ((screenWidth - (boxLength * state.getBoard().length)) / 2);

        for (int column = 0; column < state.getBoard().length; column++) {
            for(int row = 0; row < state.getBoard()[column].length; row++){
                graphics.drawRect((column * boxLength) + padding, row * boxLength, boxLength, boxLength);
                if(state.getBoard()[column][row] != null) {
                    
                    // Image Scaling from https://blog.idrsolutions.com/2019/05/image-scaling-in-java/
                    BufferedImage scaled = new BufferedImage(boxLength, boxLength, BufferedImage.TYPE_INT_ARGB);
                    final AffineTransform transform = AffineTransform.getScaleInstance(boxLength / 16.0, boxLength / 16.0);
                    final AffineTransformOp ato = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    scaled = ato.filter(state.getBoard()[column][row].getSprite(), scaled);

                    graphics.drawImage(scaled, (column * boxLength) + padding, row * boxLength, this);
                }
            }
        }

    }

    private class Listener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {

            // Game inputs
            if(inGame) {
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

                    state.getBoard()[prevX][prevY] = null;
                    state.getBoard()[state.getPlayer().getXPos()][state.getPlayer().getYPos()] = state.getPlayer();
                    state.takeTurn();
                    repaint();
                
                }
            } else {
                // Title Screen inputs
                startGame();
            }

        }

    }



    

}
