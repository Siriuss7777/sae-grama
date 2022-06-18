package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenTwo extends Screen {
    private final JLabel nodeOneSelectedTxt;
    private final JLabel nodeTwoSelectedTxt;
    private final JLabel nodeOneSelected;
    private final JLabel nodeTwoSelected;

    private Node nodeOne;
    private Node nodeTwo;

    private final JButton resetButton;
    private final JButton _2distanceButton;

    public ScreenTwo(JFrame frame, Graph graph, GraphDisplay graphDisplay) {
        super(frame, graph, graphDisplay);

        nodeOneSelectedTxt = new JLabel("Noeud un sélectionné : ");
        nodeTwoSelectedTxt = new JLabel("Noeud deux sélectionné : ");
        nodeOneSelected = new JLabel("Pas de noeud sélectionné");
        nodeTwoSelected = new JLabel("Pas de noeud sélectionné");

        resetButton = new JButton("Réinitialiser");

        _2distanceButton = new JButton("Sont-ils à 2 distance ?");


        buildPanel();
    }

    public void buildPanel(){
        panDispNodeSelected.setLayout(new GridLayout(2,2));

        panDispNodeSelected.add(nodeOneSelectedTxt);
        panDispNodeSelected.add(nodeOneSelected);
        panDispNodeSelected.add(nodeTwoSelectedTxt);
        panDispNodeSelected.add(nodeTwoSelected);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(resetButton);


        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));

        panActionNode.add(_2distanceButton);

        panDispGraph = graphDisplay.initNodeDisplay(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenTwo.this.panDispGraph.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (!e.isControlDown()) {
                        panDispGraph.getGraph().setSelectionCell(cell);
                        nodeOne = (Node) cell.getValue();
                        ScreenTwo.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else if (e.isControlDown()) {
                        panDispGraph.getGraph().setSelectionCell(cell);
                        nodeTwo = (Node) cell.getValue();
                        ScreenTwo.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
                else{
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        _2distanceButton.addActionListener(e -> {
            if (ScreenTwo.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenTwo.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenTwo.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (graph.Distance(nodeOne, nodeTwo, 2) == 2) {
                    JOptionPane.showMessageDialog(ScreenTwo.this, "Oui, ils sont à 2 distance", "Succès", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(ScreenTwo.this, "Non, ils ne sont pas à 2 distance", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(e -> {
            nodeOneSelected.setText("Pas de noeud sélectionné");
            nodeTwoSelected.setText("Pas de noeud sélectionné");
            nodeOne = null;
            nodeTwo = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }
}
