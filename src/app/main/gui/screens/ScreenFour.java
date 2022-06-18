package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class ScreenFour extends Screen {
    private final JLabel nodeOneSelectedTxt;
    private final JLabel nodeTwoSelectedTxt;
    private final JLabel nodeThreeSelectedTxt;
    private final JLabel nodeFourSelectedTxt;
    private final JLabel nodeOneSelected;
    private final JLabel nodeTwoSelected;
    private final JLabel nodeThreeSelected;
    private final JLabel nodeFourSelected;

    private Node nodeOne = null;
    private Node nodeTwo = null;
    private Node nodeThree = null;
    private Node nodeFour = null;

    private final JButton resetButton;

    private final JButton floydWarshallButton;
    private final JButton nDistanceButton;
    private final JComboBox<Integer> distance;

    private final JButton pathWithButton;

    private final JButton nDistanceMinButton;


    public ScreenFour(JFrame frame, Graph graph, GraphDisplay graphDisplay) {
        super(frame, graph, graphDisplay);
        this.frame = frame;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        nodeOneSelectedTxt = new JLabel("Noeud un sélectionné : ");
        nodeTwoSelectedTxt = new JLabel("Noeud deux sélectionné : ");
        nodeThreeSelectedTxt = new JLabel("Noeud un à traverser : ");
        nodeFourSelectedTxt = new JLabel("Noeud deux à traverser : ");
        nodeOneSelected = new JLabel("Pas de noeud sélectionné");
        nodeTwoSelected = new JLabel("Pas de noeud sélectionné");
        nodeThreeSelected = new JLabel("Pas de noeud sélectionné");
        nodeFourSelected = new JLabel("Pas de noeud sélectionné");
        resetButton = new JButton("Réinitialiser");
        floydWarshallButton = new JButton("Distance la plus courte entre les deux noeuds");
        nDistanceButton = new JButton("Distance entre les deux noeuds");
        distance = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        pathWithButton = new JButton("Chemin traversant les deux noeuds");
        nDistanceMinButton = new JButton("Distance minimum entre les deux noeuds");

        constpan();
    }

    public void constpan(){
        // TODO : Ajouter une JList pour afficher les noeuds pour les algos de path

        panDispNodeSelected.setLayout(new GridLayout(4, 2));

        panDispNodeSelected.add(nodeOneSelectedTxt);
        panDispNodeSelected.add(nodeOneSelected);
        panDispNodeSelected.add(nodeTwoSelectedTxt);
        panDispNodeSelected.add(nodeTwoSelected);
        panDispNodeSelected.add(nodeThreeSelectedTxt);
        panDispNodeSelected.add(nodeThreeSelected);
        panDispNodeSelected.add(nodeFourSelectedTxt);
        panDispNodeSelected.add(nodeFourSelected);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(resetButton);

        panActionNode.add(floydWarshallButton);

        panActionNode.add(nDistanceButton);
        panActionNode.add(distance);

        panActionNode.add(pathWithButton);

        panActionNode.add(nDistanceMinButton);

        panDispGraph = graphDisplay.initNodeDisplay(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenFour.this.panDispGraph.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (!e.isControlDown() && !e.isShiftDown()) {
                        nodeOne = (Node) cell.getValue();
                        ScreenFour.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else if (e.isControlDown() && !e.isShiftDown()) {
                        nodeTwo = (Node) cell.getValue();
                        ScreenFour.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                    else if (e.isShiftDown() && !e.isControlDown() && nodeThree == null) {
                        nodeThree = (Node) cell.getValue();
                        ScreenFour.this.nodeThreeSelected.setText(nodeThree.getName());
                    }
                    else if (e.isShiftDown() && !e.isControlDown()) {
                        nodeFour = (Node) cell.getValue();
                        ScreenFour.this.nodeFourSelected.setText(nodeFour.getName());
                    }
                }
                else{
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        floydWarshallButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ArrayList<Node> path = graph.getMatrix().getShortestPath(nodeOne, nodeTwo);
                LinkedList<Node> pathLinked = new LinkedList<>(path);
                graphDisplay.highlightPath(pathLinked);
                
            }

        });

        nDistanceButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if (graph.Distance(nodeOne,nodeTwo,distance.getSelectedIndex()+1) == distance.getSelectedIndex()+1) {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance entre les deux noeuds est de : " + (distance.getSelectedIndex()+1), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance entre les deux noeuds n'est pas de : " + (distance.getSelectedIndex()+1), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        });

        pathWithButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeThreeSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner au moins 3 noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if (nodeFourSelected.getText().equals("Pas de noeud sélectionné")){
                LinkedList<Node> path = graph.getPathWith(nodeOne, nodeTwo, nodeThree);
                graphDisplay.highlightPath(path);
            }
            else {
                LinkedList<Node> path = graph.getPathWith(nodeOne, nodeTwo, nodeThree, nodeFour);
                graphDisplay.highlightPath(path);
            }

        });

        nDistanceMinButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance minimum entre les deux noeuds est de : " + graph.Distance(nodeOne,nodeTwo,graph.getNodesCount()-1), "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        resetButton.addActionListener(e -> {
            nodeOneSelected.setText("Pas de noeud sélectionné");
            nodeTwoSelected.setText("Pas de noeud sélectionné");
            nodeThreeSelected.setText("Pas de noeud sélectionné");
            nodeFourSelected.setText("Pas de noeud sélectionné");
            nodeOne = null;
            nodeTwo = null;
            nodeThree = null;
            nodeFour = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }
}
