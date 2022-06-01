package app.main.gui.screens;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel {
    JFrame f;
    private JPanel containerTop = new JPanel();
    private JPanel containerBot = new JPanel();
    private JPanel panInfo = new JPanel();

    public MainScreen(JFrame f) {
        super();
        this.f = f;
        constpan();
    }
    private void constpan() {

        this.setLayout(new BorderLayout());


        containerTop.setSize(0, 200);
        containerTop.setPreferredSize(new Dimension(0, 200));
        containerBot.setBackground(Color.ORANGE);

        JLabel success = new JLabel("Chargé avec succès");

        containerTop.add(success);

        this.add(containerTop, BorderLayout.NORTH);
        this.add(containerBot, BorderLayout.CENTER);
    }
}

