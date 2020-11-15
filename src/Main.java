
import game.GameController;
import game.GridBagUtility;
import java.awt.*;



import javax.swing.*;

public class Main extends JFrame {

    JPanel controller;

    JLabel healthValue;
    JLabel attackValue;
    JLabel defenseValue;
    JLabel keyValue;
    JLabel floorValue;

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
        setSize(830, 900);
        setResizable(false);
        setLayout(new GridBagLayout());

        
        healthValue = new JLabel("0/0");
        JPanel healthContainer = new JPanel();
        healthContainer.add(new JLabel("Health:"));
        healthContainer.add(healthValue);

        GridBagUtility.addGBComponent(this, healthContainer, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.25, 0.10, 0, 0, 0, 0);
        
        attackValue = new JLabel("0");
        defenseValue = new JLabel("0");
        JPanel statsContainer = new JPanel();
        statsContainer.add(new JLabel("Attack:"));
        statsContainer.add(attackValue);
        statsContainer.add(new JLabel("Defense:"));
        statsContainer.add(defenseValue);
        GridBagUtility.addGBComponent(this, statsContainer, 1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.25, 0.10, 0, 0, 0, 0); //.165
        
        keyValue = new JLabel("0");
        JPanel keysContainer = new JPanel();
        keysContainer.add(new JLabel("Keys:"));
        keysContainer.add(keyValue);
        GridBagUtility.addGBComponent(this, keysContainer, 2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.25, 0.10, 0, 0, 0, 0);
        
        floorValue = new JLabel("1");
        JPanel floorContainer = new JPanel();
        floorContainer.add(new JLabel("Floor:"));
        floorContainer.add(floorValue);
        GridBagUtility.addGBComponent(this, floorContainer, 3, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.25, 0.10, 0, 0, 0, 0);
        
        controller = new GameController(healthValue, attackValue, defenseValue, keyValue, floorValue);

        controller.setBackground(Color.WHITE);
        controller.setFocusable(true);
        controller.requestFocusInWindow();
        GridBagUtility.addGBComponent(this, controller, 0, 0, 4, 2, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1, 0, 0, 0, 0);


        setLocationRelativeTo(null);
        setVisible(true);
    }

    




    
}