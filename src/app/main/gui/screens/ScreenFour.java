package app.main.gui.screens;

import app.main.gui.utils.GraphDisplay;
import app.main.map.Graph;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
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
    private final JComboBox<Integer> distanceComboBox;

    private final JButton pathWithButton;

    private final JButton nDistanceMinButton;




    public ScreenFour(JFrame frame, Graph graph, GraphDisplay graphDisplay) {
        super(frame, graph, graphDisplay);
        this.frame = frame;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        nodeOneSelectedTxt = new JLabel(FIRST_SELECTED_NODE_LABEL);
        nodeTwoSelectedTxt = new JLabel(SECOND_SELECTED_NODE_LABEL);
        nodeThreeSelectedTxt = new JLabel("Noeud un à traverser : ");
        nodeFourSelectedTxt = new JLabel("Noeud deux à traverser : ");
        nodeOneSelected = new JLabel(NO_SELECTED_NODE);
        nodeTwoSelected = new JLabel(NO_SELECTED_NODE);
        nodeThreeSelected = new JLabel(NO_SELECTED_NODE);
        nodeFourSelected = new JLabel(NO_SELECTED_NODE);
        resetButton = new JButton(RESET_LABEL);
        floydWarshallButton = new JButton("Distance la plus courte entre les deux noeuds");
        nDistanceButton = new JButton("Distance entre les deux noeuds");
        distanceComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        pathWithButton = new JButton("Chemin traversant les deux noeuds");
        nDistanceMinButton = new JButton("Distance minimum entre les deux noeuds");

        buildPanel();
    }

    public void buildPanel() {
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
        panActionNode.add(distanceComboBox);

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
                    } else if (e.isControlDown() && !e.isShiftDown()) {
                        nodeTwo = (Node) cell.getValue();
                        ScreenFour.this.nodeTwoSelected.setText(nodeTwo.getName());
                    } else if (e.isShiftDown() && !e.isControlDown() && nodeThree == null) {
                        nodeThree = (Node) cell.getValue();
                        ScreenFour.this.nodeThreeSelected.setText(nodeThree.getName());
                    } else if (e.isShiftDown() && !e.isControlDown()) {
                        nodeFour = (Node) cell.getValue();
                        ScreenFour.this.nodeFourSelected.setText(nodeFour.getName());
                    }
                } else {
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        floydWarshallButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenFour.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                ArrayList<Node> path = graph.getMatrix().getShortestPath(nodeOne, nodeTwo);
                LinkedList<Node> pathLinked = new LinkedList<>(path);
                graphDisplay.highlightPath(pathLinked);

            }

        });

        nDistanceButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenFour.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (graph.Distance(nodeOne, nodeTwo, distanceComboBox.getSelectedIndex() + 1) == distanceComboBox.getSelectedIndex() + 1) {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance entre les deux noeuds est de : " + (distanceComboBox.getSelectedIndex() + 1), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance entre les deux noeuds n'est pas de : " + (distanceComboBox.getSelectedIndex() + 1), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        });

        pathWithButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenFour.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE) || ScreenFour.this.nodeThreeSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner au moins 3 noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (nodeFourSelected.getText().equals(NO_SELECTED_NODE)) {
                LinkedList<Node> path = graph.getPathWith(nodeOne, nodeTwo, nodeThree);
                graphDisplay.highlightPath(path);
            } else {
                LinkedList<Node> path = graph.getPathWith(nodeOne, nodeTwo, nodeThree, nodeFour);
                graphDisplay.highlightPath(path);
            }

        });

        nDistanceMinButton.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenFour.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance minimum entre les deux noeuds est de : " + graph.Distance(nodeOne, nodeTwo, graph.getNodesCount() - 1), "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        resetButton.addActionListener(e -> {
            nodeOneSelected.setText(NO_SELECTED_NODE);
            nodeTwoSelected.setText(NO_SELECTED_NODE);
            nodeThreeSelected.setText(NO_SELECTED_NODE);
            nodeFourSelected.setText(NO_SELECTED_NODE);
            nodeOne = null;
            nodeTwo = null;
            nodeThree = null;
            nodeFour = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }
}
