import java.awt.*;

import game.GBUtility;

import javax.swing.*;

public class Main extends JFrame {

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
        renderWindow();
    }
    
    public void renderWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game");
        setSize(700, 394);
        setResizable(true);
        setLayout(new GridBagLayout());

        JPanel leftPad = new JPanel();
        //leftPad.setBackground(Color.GREEN);
        GBUtility.addGBComponent(this, leftPad, 0, 0, 1, 3, GridBagConstraints.BOTH, GridBagConstraints.CENTER, .10, 0.0, 0, 0, 0, 0);

        display = new JPanel();
        display.setBackground(Color.WHITE);
        GBUtility.addGBComponent(this, display, 1, 0, 4, 2, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1, 5, 0, 0, 0);

        JPanel rightPad = new JPanel();
        //rightPad.setBackground(Color.RED);
        GBUtility.addGBComponent(this, rightPad, 5, 0, 1, 3, GridBagConstraints.BOTH, GridBagConstraints.CENTER, .10, 0.0, 0, 0, 0, 0);
        
        JPanel healthContainer = new JPanel();
        healthContainer.setLayout(new BoxLayout(healthContainer, BoxLayout.Y_AXIS));
        healthContainer.add(new JLabel("Health"));
        healthContainer.add(new JLabel("[Health Bar]"));
        GBUtility.addGBComponent(this, healthContainer, 1, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.66, 0.20, 0, 0, 0, 0);

        JPanel statsContainer = new JPanel();
        statsContainer.setLayout(new BoxLayout(statsContainer, BoxLayout.Y_AXIS));
        statsContainer.add(new JLabel("Health:"));
        statsContainer.add(new JLabel("Defense:"));
        GBUtility.addGBComponent(this, statsContainer, 3, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.165, 0.20, 0, 0, 0, 0);

        
        GBUtility.addGBComponent(this, new JLabel("Keys"), 4, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.165, 0.20, 0, 0, 0, 0);

        

        

        setLocationRelativeTo(null);
        setVisible(true);
    }




    
}