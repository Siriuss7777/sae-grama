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
import java.util.LinkedList;

public class ScreenOne extends JPanel {
    JFrame f;

    Graph graph;
    GraphDisplay graphDisplay;

    private JPanel contentPane = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffNodeSel = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();

    private mxGraphComponent panAffNoeuds;

    private JLabel nodeSelectedTxt = new JLabel("Noeud sélectionné : ");
    public JLabel nodeSelected = new JLabel("Pas de noeud sélectionné");

    private Node node;

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
        int hauteur = (int)tailleEcran.getHeight();
        int largeur = (int)tailleEcran.getWidth();




        containerLeft.setSize(400, hauteur);
        containerLeft.setPreferredSize(new Dimension(400, hauteur));

        int width = largeur - 400;

        containerRight.setSize(width, hauteur);
        containerRight.setPreferredSize(new Dimension(width, hauteur));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());


        panAffNodeSel.setBorder(BorderFactory.createEtchedBorder());
        panAffNodeSel.setSize(0, 200);
        panAffNodeSel.setPreferredSize(new Dimension(0, 200));

        panActionNoeud.setLayout(new FlowLayout());
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));

        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());


        panAffNoeuds = graphDisplay.initializeAffNoeuds(new mxMouseAdapter() {
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
                else{
                    panAffNoeuds.getGraph().clearSelection();
                }
            }
        });

        neighbours.addActionListener(e -> {
            if (nodeSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(null, "Pas de noeud sélectionné");
            } else {
//                JOptionPane.showMessageDialog(null, "Voisins de " + node.getName() + " : " + node.getAllNeighbours());
                graphDisplay.selectCells(node.getNeighboursAsNodes());
            }
        });

        nodeByLink.addActionListener(e -> {
            if (nodeSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(null, "Pas de noeud sélectionné");
            } else {
                JOptionPane.showMessageDialog(null, "Node by link " + nodeSelected.getText() + ": " + "e");
            }
        });



        panAffNodeSel.add(nodeSelectedTxt);
        panAffNodeSel.add(nodeSelected);

        panActionNoeud.add(neighbours);
        panActionNoeud.add(nodeByLink);

        containerLeft.add(panAffNodeSel, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }

}
