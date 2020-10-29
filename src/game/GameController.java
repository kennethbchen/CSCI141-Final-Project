package game;

import entities.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GameController extends JPanel{

    private int boardLength;
    private int boardHeight;

    // gameBoard[0,0] is world coordinates (0,0)
    private Entity[][] gameBoard;

    private Player player;

    private boolean inGame = false;

    public GameController() {
        addKeyListener(new Listener());
    }

    public void startGame() {
        gameBoard = new Entity[16][16];

        player = new Player();
        // Generate Board

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
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(rh);

        Dimension size = getSize();
        double screenWidth = size.getWidth();
        double screenHeight = size.getHeight();

        
        double scaleFactor = (screenWidth / gameBoard.length);
        int boxLength = (int)((screenHeight) / gameBoard.length);
        int padding = (int) ((screenWidth - (boxLength * gameBoard.length)) / 2);
        System.out.println(boxLength * gameBoard.length);
        for (int column = 0; column < gameBoard.length; column++) {
            for(int row = 0; row < gameBoard[column].length; row++){
                graphics.drawRect((int)(column * boxLength) + padding, (int)(row * boxLength), (int)boxLength, (int)boxLength);
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

            } else {
                // Title Screen inputs
                startGame();
            }

            System.out.println("Key pressed " + e.getKeyCode());
        }

    }



    

}
