package app.main.gui.screens;

import app.main.gui.utils.GraphDisplay;
import app.main.map.Graph;
import app.main.nodes.Node;
import app.main.nodes.NodeType;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenThree extends Screen {
    private final JLabel nodeOneSelectedTxt;
    private final JLabel nodeTwoSelectedTxt;
    private final JLabel nodeOneSelected;
    private final JLabel nodeTwoSelected;

    private Node nodeOne;
    private Node nodeTwo;
    private Node bestNode;

    private final JButton reset;

    private final JButton gastronomique;
    private final JButton ouverte;
    private final JButton culturel;

    public ScreenThree(JFrame frame, Graph graph, GraphDisplay graphDisplay) {

        super(frame, graph, graphDisplay);

        nodeOneSelectedTxt = new JLabel(FIRST_SELECTED_NODE_LABEL);
        nodeTwoSelectedTxt = new JLabel(SECOND_SELECTED_NODE_LABEL);
        nodeOneSelected = new JLabel(NO_SELECTED_NODE);
        nodeTwoSelected = new JLabel(NO_SELECTED_NODE);
        reset = new JButton(RESET_LABEL);
        gastronomique = new JButton("Gastronomique");
        ouverte = new JButton("Ouverte");
        culturel = new JButton("Culturel");

        buildPanel();
    }

    public void buildPanel() {
        panDispNodeSelected.setLayout(new GridLayout(2, 2));

        panDispNodeSelected.add(nodeOneSelectedTxt);
        panDispNodeSelected.add(nodeOneSelected);
        panDispNodeSelected.add(nodeTwoSelectedTxt);
        panDispNodeSelected.add(nodeTwoSelected);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(reset);

        panActionNode.setLayout(new FlowLayout());
        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));
        panActionNode.add(gastronomique);
        panActionNode.add(ouverte);
        panActionNode.add(culturel);

        panDispGraph = graphDisplay.initNodeDisplay(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenThree.this.panDispGraph.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (!e.isControlDown()) {
                        nodeOne = (Node) cell.getValue();
                        ScreenThree.this.nodeOneSelected.setText(nodeOne.getName());
                    } else if (e.isControlDown()) {
                        nodeTwo = (Node) cell.getValue();
                        ScreenThree.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                } else {
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        gastronomique.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenThree.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (this.nodeOne.getType() == NodeType.CITY && this.nodeTwo.getType() == NodeType.CITY) {
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "GASTRONOMIQUE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus gastronomique !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        ouverte.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenThree.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (this.nodeOne.getType() == NodeType.CITY && this.nodeTwo.getType() == NodeType.CITY) {
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "OUVERTE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus ouverte !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        culturel.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals(NO_SELECTED_NODE) || ScreenThree.this.nodeTwoSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (this.nodeOne.getType() == NodeType.CITY && this.nodeTwo.getType() == NodeType.CITY) {
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "CULTURELLE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus culturelle !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        reset.addActionListener(e -> {
            nodeOneSelected.setText(NO_SELECTED_NODE);
            nodeTwoSelected.setText(NO_SELECTED_NODE);
            nodeOne = null;
            nodeTwo = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }
}
