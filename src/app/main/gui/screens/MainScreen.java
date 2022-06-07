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
    GraphDisplay graphDisplay;

    public MainScreen(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super();
        this.f = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;
        constpan();
    }
    private void constpan() {

        this.setLayout(new BorderLayout());


        containerTop.setSize(0, 200);
        containerTop.setPreferredSize(new Dimension(0, 200));
        containerTop.setBorder(BorderFactory.createEtchedBorder());


        containerBot = this.graphDisplay.initializeAffNoeuds(GraphDisplay.DEFAULT_MOUSELISTENER);


        // Dis si le graphe a bien été initialisé
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

