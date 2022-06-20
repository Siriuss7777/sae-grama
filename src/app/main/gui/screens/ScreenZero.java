package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ScreenZero extends Screen {
    private final JLabel citiesCountLabel;
    private final JLabel restaurantCountLabel;
    private final JLabel leisureCountLabel;
    private final JLabel nationalRoadsCountLabel;
    private final JLabel highwayCountLabel;
    private final JLabel departmentalRoadsCountLabel;

    private final JLabel citiesListLabel;
    private final JLabel restaurantListLabel;
    private final JLabel leisureListLabel;
    private final JLabel nationalRoadsListLabel;
    private final JLabel highwayListLabel;
    private final JLabel departmentalRoadsListLabel;

    private JComboBox<String> cityListComboBox;
    private JComboBox<String> restaurantListComboBox;
    private JComboBox<String> leisureListComboBox;

    private JComboBox<String> departmentalRoadsListComboBox;
    private JComboBox<String> nationalRoadsListComboBox;
    private JComboBox<String> highwayListComboBox;

    private final LinkedList<Node> selectedNodesList;
    private final LinkedList<Link> selectedLinksList;

    public ScreenZero(JFrame frame, Graph graph, GraphDisplay graphDisplay) {

        super(frame, graph, graphDisplay);

        citiesCountLabel = new JLabel("Nombre de villes : ");
        restaurantCountLabel = new JLabel("Nombre de restaurants : ");
        leisureCountLabel = new JLabel("Nombre de centres de loisir : ");

        citiesListLabel = new JLabel("Liste des villes : ");
        restaurantListLabel = new JLabel("Liste des restaurants : ");
        leisureListLabel = new JLabel("Liste des centres de loisir : ");


        nationalRoadsCountLabel = new JLabel("Nombre de nationales : ");
        highwayCountLabel = new JLabel("Nombre d'autoroutes : ");
        departmentalRoadsCountLabel = new JLabel("Nombre de départementales : ");

        nationalRoadsListLabel = new JLabel("Liste des nationales : ");
        highwayListLabel = new JLabel("Liste des autoroutes : ");
        departmentalRoadsListLabel = new JLabel("Liste des départementales : ");


        selectedNodesList = new LinkedList<>();
        selectedLinksList = new LinkedList<>();

        buildPanel();
    }

    private void buildPanel() {
        leftCorner.setLayout(new GridLayout(6, 2));

        leftCorner.setSize(0, 200);
        leftCorner.setPreferredSize(new Dimension(0, 200));

        panActionNode.setLayout(new GridLayout(2, 6));
        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));


        panDispGraph = graphDisplay.initNodeDisplay(GraphDisplay.DEFAULT_MOUSELISTENER);

        leftCorner.add(citiesCountLabel);
        leftCorner.add(new JLabel(String.valueOf(graph.getCitiesCount())));
        leftCorner.add(restaurantCountLabel);
        leftCorner.add(new JLabel(String.valueOf(graph.getRestaurantsCount())));
        leftCorner.add(leisureCountLabel);
        leftCorner.add(new JLabel(String.valueOf(graph.getLeisuresCount())));
        leftCorner.add(departmentalRoadsCountLabel);
        leftCorner.add(new JLabel(String.valueOf(graph.getDepartmentalRoadsCount())));
        leftCorner.add(nationalRoadsCountLabel);
        leftCorner.add(new JLabel(String.valueOf(graph.getNationalRoadsCount())));
        leftCorner.add(highwayCountLabel);
        leftCorner.add(new JLabel(String.valueOf(graph.getHighwaysCount())));

        panActionNode.add(citiesListLabel);
        panActionNode.add(restaurantListLabel);
        panActionNode.add(leisureListLabel);
        panActionNode.add(departmentalRoadsListLabel);
        panActionNode.add(nationalRoadsListLabel);
        panActionNode.add(highwayListLabel);

        cityListComboBox = new JComboBox<>(graph.getCitiesNames());
        restaurantListComboBox = new JComboBox<>(graph.getRestaurantsNames());
        leisureListComboBox = new JComboBox<>(graph.getLeisuresNames());

        panActionNode.add(cityListComboBox);
        panActionNode.add(restaurantListComboBox);
        panActionNode.add(leisureListComboBox);


        cityListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedNodesList.clear();
                selectedNodesList.add(graph.getNodeFromString("V," + cityListComboBox.getSelectedItem().toString()));
                graphDisplay.highlightNodes(selectedNodesList);
            }
        });

        restaurantListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedNodesList.clear();
                selectedNodesList.add(graph.getNodeFromString("R," + restaurantListComboBox.getSelectedItem().toString()));
                graphDisplay.highlightNodes(selectedNodesList);
            }
        });

        leisureListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedNodesList.clear();
                selectedNodesList.add(graph.getNodeFromString("L," + leisureListComboBox.getSelectedItem().toString()));
                graphDisplay.highlightNodes(selectedNodesList);
            }
        });

        departmentalRoadsListComboBox = new JComboBox<>(graph.getDepartmentalRoadsNames());
        nationalRoadsListComboBox = new JComboBox<>(graph.getNationalRoadsNames());
        highwayListComboBox = new JComboBox<>(graph.getHighwaysNames());

        panActionNode.add(departmentalRoadsListComboBox);
        panActionNode.add(nationalRoadsListComboBox);
        panActionNode.add(highwayListComboBox);

        departmentalRoadsListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLinksList.clear();
                selectedLinksList.add(graph.getLinkFromString(departmentalRoadsListComboBox.getSelectedItem().toString()));
                graphDisplay.highlightLinks(selectedLinksList);
            }
        });

        nationalRoadsListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLinksList.clear();
                selectedLinksList.add(graph.getLinkFromString(nationalRoadsListComboBox.getSelectedItem().toString()));
                graphDisplay.highlightLinks(selectedLinksList);
            }
        });

        highwayListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLinksList.clear();
                selectedLinksList.add(graph.getLinkFromString(highwayListComboBox.getSelectedItem().toString()));
                graphDisplay.highlightLinks(selectedLinksList);
            }
        });

        containerLeft.add(leftCorner, BorderLayout.NORTH);
        containerLeft.add(panKey, BorderLayout.CENTER);

        containerRight.add(panActionNode, BorderLayout.NORTH);
        containerRight.add(panDispGraph, BorderLayout.CENTER);

    }
}
