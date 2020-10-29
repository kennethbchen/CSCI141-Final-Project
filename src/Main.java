
import game.GameController;
import java.awt.*;



import javax.swing.*;

public class Main extends JFrame {

    JPanel controller;
    JPanel display;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main app = new Main();
            }
        });
    }

    public Main() {
        init();
    }

    public void init() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game");
        setSize(700, 700);
        setResizable(true);

        controller = new GameController();

        controller.setBackground(Color.WHITE);
        add(controller);

        controller.setFocusable(true);
        controller.requestFocusInWindow();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    




    
}