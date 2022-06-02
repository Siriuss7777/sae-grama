package app.main.gui.screens;

import app.main.map.Graph;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;

public class ScreenZero extends JPanel {
    JFrame f;

    Graph graph;
    GraphDisplay graphDisplay;
    private JPanel contentPane = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffGen = new JPanel();
    private JPanel panAffParType = new JPanel();
    private JPanel panListeNoeud = new JPanel();
    private mxGraphComponent panAffNoeuds;

    private JLabel nbrVille = new JLabel("Nombre de villes : ");
    private JLabel nbrRest = new JLabel("Nombre de restaurants : ");
    private JLabel nbrLoisir = new JLabel("Nombre de centres de loisir : ");
    private JLabel nbrNat = new JLabel("Nombre de nationales : ");
    private JLabel nbrAuto = new JLabel("Nombre d'autoroutes : ");
    private JLabel nbrDep = new JLabel("Nombre de départementales : ");

    private JLabel listeVille = new JLabel("Liste des villes : ");
    private JLabel listeRest = new JLabel("Liste des restaurants : ");
    private JLabel listeLoisir = new JLabel("Liste des centres de loisir : ");
    private JLabel listeNat = new JLabel("Liste des nationales : ");
    private JLabel listeAuto = new JLabel("Liste des autoroutes : ");
    private JLabel listeDep = new JLabel("Liste des départementales : ");

    public ScreenZero(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super();
        this.f = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;
        constpan();
    }
    private void constpan() {

        this.setLayout(new BorderLayout());

        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        panAffGen.setLayout(new GridLayout(6, 2));

        panAffGen.setBorder(BorderFactory.createEtchedBorder());
        panAffGen.setSize(0, 200);
        panAffGen.setPreferredSize(new Dimension(0, 200));

        panAffParType.setLayout(new GridLayout(2, 6));
        panAffParType.setBorder(BorderFactory.createEtchedBorder());
        panAffParType.setSize(0, 200);
        panAffParType.setPreferredSize(new Dimension(0, 200));



        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        panAffNoeuds = graphDisplay.initializeAffNoeuds(GraphDisplay.DEFAULT_MOUSELISTENER);


        panAffGen.add(nbrVille);
        panAffGen.add(new JLabel(String.valueOf(graph.getVillesCount()))); // Recupere le nbr de ville et le met en String
        panAffGen.add(nbrRest);
        panAffGen.add(new JLabel(String.valueOf(graph.getRestaurantsCount())));
        panAffGen.add(nbrLoisir);
        panAffGen.add(new JLabel(String.valueOf(graph.getLoisirsCount())));
        panAffGen.add(nbrDep);
        panAffGen.add(new JLabel(String.valueOf(graph.getDepartementalesCount())));
        panAffGen.add(nbrNat);
        panAffGen.add(new JLabel(String.valueOf(graph.getNationalesCount())));
        panAffGen.add(nbrAuto);
        panAffGen.add(new JLabel(String.valueOf(graph.getAutoroutesCount())));

        panAffParType.add(listeVille);
        panAffParType.add(listeRest);
        panAffParType.add(listeLoisir);
        panAffParType.add(listeDep);
        panAffParType.add(listeNat);
        panAffParType.add(listeAuto);
        panAffParType.add(new JComboBox<>(graph.getVillesNames()));
        panAffParType.add(new JComboBox<>(graph.getRestaurantsNames()));
        panAffParType.add(new JComboBox<>(graph.getLoisirsNames()));
        panAffParType.add(new JComboBox<>(graph.getDepartementalesNames()));
        panAffParType.add(new JComboBox<>(graph.getNationalesNames()));
        panAffParType.add(new JComboBox<>(graph.getAutoroutesNames()));



        containerLeft.add(panAffGen, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panAffParType, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);

    }
}
