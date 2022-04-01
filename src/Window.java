import javax.swing.*;
import java.awt.*;

public class Window {
    private String title;
    private final int width;
    private final int height;

    public Window(){
        title = "GRAMA - GUENOT/LE GALL";
        width = 1280;
        height = 720;
    }

    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    public void init() {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(true);

    }

    public void add(Component component) {
        panel.add(component);
    }


}
