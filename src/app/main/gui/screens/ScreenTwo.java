package app.main.gui.screens;

import app.main.map.Graph;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;

public class ScreenTwo extends JPanel {
    JFrame f;

    Graph graph;

    private JPanel contentPane = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffGen = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();
    private mxGraphComponent panAffNoeuds;

    private JLabel nodeSelectedTxt = new JLabel("Node selected: ");
    public JLabel nodeSelected = new JLabel("No node selected");

    public ScreenTwo(JFrame f, Graph graph) {
        super();
        this.f = f;
        this.graph = graph;
        constpan();
    }

    public void constpan(){

        this.setLayout(new BorderLayout());

        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        panAffGen.setLayout(new GridLayout(6, 2));
        //panAffGen.setBackground(Color.RED);
        panAffGen.setBorder(BorderFactory.createEtchedBorder());
        panAffGen.setSize(0, 200);
        panAffGen.setPreferredSize(new Dimension(0, 200));

        //panActionNoeud.setBackground(Color.BLACK);
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));


        //panListeNoeud.setBackground(Color.BLUE);
        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        GraphDisplay gd = new GraphDisplay(graph);
        panAffNoeuds = gd.initializeAffNoeuds();


        containerLeft.add(panAffGen, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
