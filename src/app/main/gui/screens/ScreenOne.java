package app.main.gui.screens;

import app.main.gui.utils.GraphDisplay;
import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenOne extends Screen {
    private final JLabel labelNodeSelected;
    private final JLabel nodeSelected;

    private final JLabel linkSelectedTxt;
    private final JLabel linkSelected;

    private Node node;
    private Link link;

    private final JButton resetButton;

    private final JButton neighboursButton;
    private final JButton nodeByLinkButton;


    public ScreenOne(JFrame frame, Graph graph, GraphDisplay graphDisplay) {

        super(frame, graph, graphDisplay);

        labelNodeSelected = new JLabel(SELECTED_NODE_LABEL);
        nodeSelected = new JLabel(NO_SELECTED_NODE);
        linkSelectedTxt = new JLabel(SELECTED_LINK_LABEL);
        linkSelected = new JLabel(NO_SELECTED_NODE);

        resetButton = new JButton(RESET_LABEL);

        neighboursButton = new JButton("Voisins");
        nodeByLinkButton = new JButton("Noeuds relié par le lien");

        buildPanel();
    }

    private void buildPanel() {
        panDispNodeSelected.setLayout(new GridLayout(2, 2));

        panDispNodeSelected.add(labelNodeSelected);
        panDispNodeSelected.add(nodeSelected);
        panDispNodeSelected.add(linkSelectedTxt);
        panDispNodeSelected.add(linkSelected);
        panDispNodeSelected.add(resetButton);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(resetButton);

        panActionNode.setLayout(new FlowLayout());
        panActionNode.setBorder(BorderFactory.createEtchedBorder());
        panActionNode.setSize(0, 200);
        panActionNode.setPreferredSize(new Dimension(0, 200));
        panActionNode.add(neighboursButton);
        panActionNode.add(nodeByLinkButton);


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
                } else if (cell != null && cell.isEdge()) {
                    link = graphDisplay.findLink(cell);
                    linkSelected.setText(link.toString());
                    // Select the cell
                    panDispGraph.getGraph().setSelectionCell(cell);
                } else {
                    panDispGraph.getGraph().clearSelection();
                }
            }
        });

        neighboursButton.addActionListener(e -> {
            if (nodeSelected.getText().equals(NO_SELECTED_NODE)) {
                JOptionPane.showMessageDialog(null, NO_SELECTED_NODE);
            } else {
                graphDisplay.highlightNodes(node.getNeighboursAsNodes());
                graphDisplay.highlightLinks(node.getAllNeighbours());

            }
        });

        nodeByLinkButton.addActionListener(e -> {
            if (linkSelected.getText().equals(NO_SELECTED_LINK)) {
                JOptionPane.showMessageDialog(null, NO_SELECTED_LINK);
            } else {
                graphDisplay.highlightNodesByLink(link);
            }
        });

        resetButton.addActionListener(e -> {
            nodeSelected.setText(NO_SELECTED_NODE);
            linkSelected.setText(NO_SELECTED_LINK);
            node = null;
            link = null;
            graphDisplay.resetColours();
        });

        addAllPanels();
    }

}
