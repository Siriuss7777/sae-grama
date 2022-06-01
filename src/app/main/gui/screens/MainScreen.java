package app.main.gui.screens;

import app.main.map.Graph;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel {
    JFrame f;
    private JPanel containerTop = new JPanel();
    private mxGraphComponent containerBot;
    private JPanel panInfo = new JPanel();

    Graph graph;

    public MainScreen(JFrame f, Graph graph) {
        super();
        this.f = f;
        this.graph = graph;
        constpan();
    }
    private void constpan() {

        this.setLayout(new BorderLayout());


        containerTop.setSize(0, 200);
        containerTop.setPreferredSize(new Dimension(0, 200));
        containerTop.setBorder(BorderFactory.createEtchedBorder());


        GraphDisplay gd = new GraphDisplay(graph);
        containerBot = gd.initializeAffNoeuds();


        // Tell if display have been initialized successfully
        if (containerBot != null) {
            JLabel success = new JLabel("Chargé avec succès");
            containerTop.add(success);
            this.add(containerBot, BorderLayout.CENTER);
        } else {
            JLabel fail = new JLabel("Erreur de chargement");
            containerTop.add(fail);
        }

        this.add(containerTop, BorderLayout.NORTH);
    }
}

