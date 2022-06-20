package app.main.gui.screens;

import app.main.gui.utils.GraphDisplay;
import app.main.gui.utils.Key;
import app.main.map.Graph;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    JFrame frame;

    Graph graph;
    GraphDisplay graphDisplay;

    final JPanel containerLeft;
    final JPanel containerRight;

    final JPanel leftCorner;
    final JPanel panActionNode;
    final JPanel panKey;
    mxGraphComponent panDispGraph;

    final JPanel panDispNodeSelected;
    final JPanel resetPan;

    final static String NO_SELECTED_NODE = "Pas de noeud sélectionné";
    final static String NO_SELECTED_LINK = "Pas de lien sélectionné";
    final static String SELECTED_NODE_LABEL = "Noeud sélectionné: ";
    final static String SELECTED_LINK_LABEL = "Lien sélectionné: ";
    final static String FIRST_SELECTED_NODE_LABEL = "Noeud un sélectionné: ";
    final static String SECOND_SELECTED_NODE_LABEL = "Noeud deux sélectionné: ";
    final static String RESET_LABEL = "Réinitialiser";

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
        panKey = new Key();


        buildPanel();

    }

    private void buildPanel(){
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight();
        int width = (int) screenSize.getWidth();
        int newWidth = width - 400;

        this.setLayout(new BorderLayout());

        this.setSize(width, height-150);
        this.setPreferredSize(new Dimension(width, height-150));

        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerRight.setSize(newWidth, height);
        containerRight.setPreferredSize(new Dimension(newWidth, height));

        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));

        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());


        leftCorner.setLayout(new BorderLayout());
        leftCorner.setBorder(BorderFactory.createEtchedBorder());

        panDispNodeSelected.setSize(0, 150);
        panDispNodeSelected.setPreferredSize(new Dimension(0, 150));

        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));

        panKey.setBorder(BorderFactory.createEtchedBorder());

        panDispGraph = graphDisplay.initNodeDisplay(GraphDisplay.DEFAULT_MOUSELISTENER);
    }

    void addAllPanels(){
        leftCorner.add(panDispNodeSelected, BorderLayout.NORTH);
        leftCorner.add(resetPan, BorderLayout.SOUTH);

        containerLeft.add(leftCorner, BorderLayout.NORTH);
        containerLeft.add(panKey, BorderLayout.CENTER);

        containerRight.add(panActionNode, BorderLayout.NORTH);
        containerRight.add(panDispGraph, BorderLayout.CENTER);
    }


}
