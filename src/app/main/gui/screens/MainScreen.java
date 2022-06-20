package app.main.gui.screens;

import app.main.map.Graph;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel {
    JFrame frame;
    private final JPanel containerTop;
    private mxGraphComponent containerBottom;
    JLabel failLabel;
    JLabel successLabel;

    Graph graph;
    GraphDisplay graphDisplay;

    public MainScreen(JFrame frame, Graph graph, GraphDisplay graphDisplay) {
        super();
        this.frame = frame;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        containerTop = new JPanel();

        successLabel = new JLabel("Chargé avec succès");
        failLabel = new JLabel("Erreur de chargement");

        buildPanel();

    }

    private void buildPanel() {

        this.setLayout(new BorderLayout());


        containerTop.setSize(0, 200);
        containerTop.setPreferredSize(new Dimension(0, 200));
        containerTop.setBorder(BorderFactory.createEtchedBorder());


        containerBottom = this.graphDisplay.initNodeDisplay(GraphDisplay.DEFAULT_MOUSELISTENER);


        if (containerBottom != null) {
            containerTop.add(successLabel);
            this.add(containerBottom, BorderLayout.CENTER);
        } else {
            containerTop.add(failLabel);
        }

        this.add(containerTop, BorderLayout.NORTH);
    }
}

