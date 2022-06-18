package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import app.main.nodes.NodeType;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
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

    public ScreenThree(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super(f, graph, graphDisplay);
        this.frame = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        nodeOneSelectedTxt = new JLabel("Noeud un sélectionné : ");
        nodeTwoSelectedTxt = new JLabel("Noeud deux sélectionné : ");
        nodeOneSelected = new JLabel("Pas de noeud sélectionné");
        nodeTwoSelected = new JLabel("Pas de noeud sélectionné");
        reset = new JButton("Réinitialiser");
        gastronomique = new JButton("Gastronomique");
        ouverte = new JButton("Ouverte");
        culturel = new JButton("Culturel");

        constpan();
    }

    public void constpan(){
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
                    }
                    else if (e.isControlDown()) {
                        nodeTwo = (Node) cell.getValue();
                        ScreenThree.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
                else{
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        gastronomique.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenThree.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if(this.nodeOne.getType() == NodeType.VILLE && this.nodeTwo.getType() == NodeType.VILLE){
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "GASTRONOMIQUE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus gastronomique !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        ouverte.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenThree.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if(this.nodeOne.getType() == NodeType.VILLE && this.nodeTwo.getType() == NodeType.VILLE){
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "OUVERTE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus ouverte !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        culturel.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenThree.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if(this.nodeOne.getType() == NodeType.VILLE && this.nodeTwo.getType() == NodeType.VILLE){
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "CULTURELLE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus culturelle !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        reset.addActionListener(e -> {
            nodeOneSelected.setText("Pas de noeud sélectionné");
            nodeTwoSelected.setText("Pas de noeud sélectionné");
            nodeOne = null;
            nodeTwo = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }
}
