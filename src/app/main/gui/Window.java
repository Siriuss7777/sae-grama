package app.main.gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private String title;

    public Window(){
        super();
        init();
    }

    JPanel panel = new JPanel();

    public void init() {
        setTitle("SAE GRAMA - Guenot/Le Gall");
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


}
