package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ScreenZero extends Screen {
    private final JLabel nbrVille;
    private final JLabel nbrRest;
    private final JLabel nbrLoisir;
    private final JLabel nbrNat;
    private final JLabel nbrAuto;
    private final JLabel nbrDep;

    private final JLabel listeVille;
    private final JLabel listeRest;
    private final JLabel listeLoisir;
    private final JLabel listeNat;
    private final JLabel listeAuto;
    private final JLabel listeDep;

    private JComboBox<String> listeVilleCombo;
    private JComboBox<String> listeRestCombo;
    private JComboBox<String> listeLoisirCombo;

    private JComboBox<String> listeDepCombo;
    private JComboBox<String> listeNatCombo;
    private JComboBox<String> listeAutCombo;

    private final LinkedList<Node> listeNoeudsSelect;
    private final LinkedList<Link> listeLienSelect;

    public ScreenZero(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super(f, graph, graphDisplay);
        this.frame = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        nbrVille = new JLabel("Nombre de villes : ");
        nbrRest = new JLabel("Nombre de restaurants : ");
        nbrLoisir = new JLabel("Nombre de centres de loisir : ");
        nbrNat = new JLabel("Nombre de nationales : ");
        nbrAuto = new JLabel("Nombre d'autoroutes : ");
        nbrDep = new JLabel("Nombre de départementales : ");
        listeVille = new JLabel("Liste des villes : ");
        listeRest = new JLabel("Liste des restaurants : ");
        listeLoisir = new JLabel("Liste des centres de loisir : ");
        listeNat = new JLabel("Liste des nationales : ");
        listeAuto = new JLabel("Liste des autoroutes : ");
        listeDep = new JLabel("Liste des départementales : ");
        listeNoeudsSelect = new LinkedList<>();
        listeLienSelect = new LinkedList<>();

        constpan();
    }
    private void constpan() {
        leftCorner.setLayout(new GridLayout(6, 2));

        leftCorner.setSize(0, 200);
        leftCorner.setPreferredSize(new Dimension(0, 200));

        panActionNode.setLayout(new GridLayout(2, 6));
        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));


        panDispGraph = graphDisplay.initNodeDisplay(GraphDisplay.DEFAULT_MOUSELISTENER);

        leftCorner.add(nbrVille);
        leftCorner.add(new JLabel(String.valueOf(graph.getVillesCount()))); // Recupere le nbr de ville et le met en String
        leftCorner.add(nbrRest);
        leftCorner.add(new JLabel(String.valueOf(graph.getRestaurantsCount())));
        leftCorner.add(nbrLoisir);
        leftCorner.add(new JLabel(String.valueOf(graph.getLoisirsCount())));
        leftCorner.add(nbrDep);
        leftCorner.add(new JLabel(String.valueOf(graph.getDepartementalesCount())));
        leftCorner.add(nbrNat);
        leftCorner.add(new JLabel(String.valueOf(graph.getNationalesCount())));
        leftCorner.add(nbrAuto);
        leftCorner.add(new JLabel(String.valueOf(graph.getAutoroutesCount())));

        panActionNode.add(listeVille);
        panActionNode.add(listeRest);
        panActionNode.add(listeLoisir);
        panActionNode.add(listeDep);
        panActionNode.add(listeNat);
        panActionNode.add(listeAuto);

        listeVilleCombo = new JComboBox<>(graph.getVillesNames());
        listeRestCombo = new JComboBox<>(graph.getRestaurantsNames());
        listeLoisirCombo = new JComboBox<>(graph.getLoisirsNames());

        panActionNode.add(listeVilleCombo);
        panActionNode.add(listeRestCombo);
        panActionNode.add(listeLoisirCombo);

        // TODO : Régler le surglignage des noeuds
        // TODO : Surligner les liens sélectionnés
        listeVilleCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeNoeudsSelect.clear();
                listeNoeudsSelect.add(graph.getNodeFromString("V," + listeVilleCombo.getSelectedItem().toString()));
                graphDisplay.highlightNodes(listeNoeudsSelect);
            }
        });

        listeRestCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeNoeudsSelect.clear();
                listeNoeudsSelect.add(graph.getNodeFromString("R," + listeRestCombo.getSelectedItem().toString()));
                graphDisplay.highlightNodes(listeNoeudsSelect);
            }
        });

        listeLoisirCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeNoeudsSelect.clear();
                listeNoeudsSelect.add(graph.getNodeFromString("L," + listeLoisirCombo.getSelectedItem().toString()));
                graphDisplay.highlightNodes(listeNoeudsSelect);
            }
        });

        listeDepCombo = new JComboBox<>(graph.getDepartementalesNames());
        listeNatCombo = new JComboBox<>(graph.getNationalesNames());
        listeAutCombo = new JComboBox<>(graph.getAutoroutesNames());

        panActionNode.add(listeDepCombo);
        panActionNode.add(listeNatCombo);
        panActionNode.add(listeAutCombo);

        listeDepCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeLienSelect.clear();
                listeLienSelect.add(graph.getLinkFromString(listeDepCombo.getSelectedItem().toString()));
                graphDisplay.highlightLinks(listeLienSelect);
            }
        });

        listeNatCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeLienSelect.clear();
                listeLienSelect.add(graph.getLinkFromString(listeNatCombo.getSelectedItem().toString()));
                graphDisplay.highlightLinks(listeLienSelect);
            }
        });

        listeAutCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeLienSelect.clear();
                listeLienSelect.add(graph.getLinkFromString(listeAutCombo.getSelectedItem().toString()));
                graphDisplay.highlightLinks(listeLienSelect);
            }
        });

        containerLeft.add(leftCorner, BorderLayout.NORTH);
        containerLeft.add(panKey, BorderLayout.CENTER);

        containerRight.add(panActionNode, BorderLayout.NORTH);
        containerRight.add(panDispGraph, BorderLayout.CENTER);

    }
}
