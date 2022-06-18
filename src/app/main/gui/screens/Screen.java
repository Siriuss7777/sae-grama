package app.main.gui.screens;

import app.main.map.Graph;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    JFrame frame;

    Graph graph;
    GraphDisplay graphDisplay;


    final JPanel containerLeft;
    final JPanel containerRight;
    final JPanel leftCorner;
    final JPanel resetPan;
    final JPanel panDispNodeSelected;
    final JPanel panActionNode;
    final JPanel panListNodes;

    public Screen(JFrame frame, Graph graph, GraphDisplay graphDisplay) {
        super();
        this.frame = frame;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        containerLeft = new JPanel();
        containerRight = new JPanel();

        leftCorner = new JPanel();
        resetPan = new JPanel();
        panDispNodeSelected = new JPanel();
        panActionNode = new JPanel();
        panListNodes = new JPanel();

        buildPanel();

    }

    private void buildPanel(){

        this.setLayout(new BorderLayout());

        this.add(this.containerLeft, BorderLayout.WEST);

        this.add(containerRight, BorderLayout.CENTER);

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight();
        int width = (int) screenSize.getWidth();


        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));

        this.setSize(width, height-150);
        this.setPreferredSize(new Dimension(width, height-150));

        int newWidth = width - 400;

        containerRight.setSize(newWidth, height);
        containerRight.setPreferredSize(new Dimension(newWidth, height));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        leftCorner.setLayout(new BorderLayout());
        leftCorner.setBorder(BorderFactory.createEtchedBorder());

        panDispNodeSelected.setLayout(new GridLayout(3, 2));
        panDispNodeSelected.setSize(0, 150);
        panDispNodeSelected.setPreferredSize(new Dimension(0, 150));

        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));

    }


}
