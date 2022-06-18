package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenOne extends Screen {
    private final JLabel nodeSelectedTxt;
    private final JLabel nodeSelected;

    private final JLabel linkSelectedTxt;
    private final JLabel linkSelected;

    private Node node;
    private Link link;

    private final JButton reset;

    private final JButton neighbours;
    private final JButton nodeByLink;


    public ScreenOne(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super(f, graph, graphDisplay);
        this.frame = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;

        nodeSelectedTxt = new JLabel("Noeud sélectionné : ");
        nodeSelected = new JLabel("Pas de noeud sélectionné");
        linkSelectedTxt = new JLabel("Lien sélectionné : ");
        linkSelected = new JLabel("Pas de lien sélectionné");
        reset = new JButton("Réinitialiser");
        neighbours = new JButton("Voisins");
        nodeByLink = new JButton("Noeuds relié par le lien");

        constpan();
    }

    private void constpan(){
        panDispNodeSelected.setLayout(new GridLayout(2, 2));

        panDispNodeSelected.add(nodeSelectedTxt);
        panDispNodeSelected.add(nodeSelected);
        panDispNodeSelected.add(linkSelectedTxt);
        panDispNodeSelected.add(linkSelected);
        panDispNodeSelected.add(reset);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(reset);

        panActionNode.setLayout(new FlowLayout());
        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));
        panActionNode.add(neighbours);
        panActionNode.add(nodeByLink);


        panDispGraph = graphDisplay.initNodeDisplay(new mxMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mxCell cell = (mxCell) panDispGraph.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    node = (Node) cell.getValue();
                    nodeSelected.setText(node.getName());
                    // Select the cell
                    panDispGraph.getGraph().setSelectionCell(cell);
                }
                else if (cell != null && cell.isEdge()){
                    link = graphDisplay.findLink(cell);
                    linkSelected.setText(link.toString());
                    // Select the cell
                    panDispGraph.getGraph().setSelectionCell(cell);
                }

                else{
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        neighbours.addActionListener(e -> {
            if (nodeSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(null, "Pas de noeud sélectionné");
            } else {
                graphDisplay.highlightNodes(node.getNeighboursAsNodes());
                graphDisplay.highlightLinks(node.getAllNeighbours());

            }
        });

        nodeByLink.addActionListener(e -> {
            if (linkSelected.getText().equals("Pas de lien sélectionné")) {
                JOptionPane.showMessageDialog(null, "Pas de lien sélectionné");
            } else {
                graphDisplay.highlightNodesByLink(link);
            }
        });

        reset.addActionListener(e -> {
            nodeSelected.setText("Pas de noeud sélectionné");
            linkSelected.setText("Pas de lien sélectionné");
            node = null;
            link = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }

}
