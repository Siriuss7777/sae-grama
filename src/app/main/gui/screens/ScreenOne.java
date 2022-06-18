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

public class ScreenOne extends JPanel {
    JFrame f;

    Graph graph;
    GraphDisplay graphDisplay;

    private JPanel contentPane = new JPanel();

    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();

    private JPanel leftCorner = new JPanel();

    private JPanel resetPan = new JPanel();

    private JPanel panAffNodeSel = new JPanel();

    private JPanel panActionNoeud = new JPanel();
    private JPanel panKey = new JPanel();

    private mxGraphComponent panAffNoeuds;

    private JLabel nodeSelectedTxt = new JLabel("Noeud sélectionné : ");
    public JLabel nodeSelected = new JLabel("Pas de noeud sélectionné");

    private JLabel linkSelectedTxt = new JLabel("Lien sélectionné : ");
    public JLabel linkSelected = new JLabel("Pas de lien sélectionné");

    private Node node;
    private Link link;

    private JButton reset = new JButton("Reset");

    private JButton neighbours = new JButton("Voisins");
    private JButton nodeByLink = new JButton("Noeuds relié par le lien");

    public JLabel getNodeSelected() {
        return nodeSelected;
    }

    public ScreenOne(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super();
        this.f = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;
        constpan();
    }

    private void constpan(){
        this.setLayout(new BorderLayout());

        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);

        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)tailleEcran.getHeight();
        int width = (int)tailleEcran.getWidth();

        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));

        this.setSize(width, height-150);
        this.setPreferredSize(new Dimension(width, height-150));

        int newWidth = width - 400;

        containerRight.setSize(newWidth, height);
        containerRight.setPreferredSize(new Dimension(newWidth, height));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        leftCorner.setLayout(new BorderLayout());
        leftCorner.setBorder(BorderFactory.createEtchedBorder());


        panAffNodeSel.setPreferredSize(new Dimension(0, 150));
        panAffNodeSel.add(nodeSelectedTxt);
        panAffNodeSel.add(nodeSelected);
        panAffNodeSel.add(linkSelectedTxt);
        panAffNodeSel.add(linkSelected);
        panAffNodeSel.add(reset);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(reset);

        panActionNoeud.setLayout(new FlowLayout());
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));
        panActionNoeud.add(neighbours);
        panActionNoeud.add(nodeByLink);

        panKey.setBorder(BorderFactory.createEtchedBorder());


        panAffNoeuds = graphDisplay.initNodeDisplay(new mxMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mxCell cell = (mxCell) panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    node = (Node) cell.getValue();
                    nodeSelected.setText(node.getName());
                    // Select the cell
                    panAffNoeuds.getGraph().setSelectionCell(cell);
                }
                else if (cell != null && cell.isEdge()){
                    link = graphDisplay.findLink(cell);
                    linkSelected.setText(link.toString());
                    // Select the cell
                    panAffNoeuds.getGraph().setSelectionCell(cell);
                }

                else{
                    panAffNoeuds.getGraph().clearSelection();
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


        leftCorner.add(panAffNodeSel, BorderLayout.NORTH);
        leftCorner.add(resetPan, BorderLayout.SOUTH);

        containerLeft.add(leftCorner, BorderLayout.NORTH);
        containerLeft.add(panKey, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }

}
