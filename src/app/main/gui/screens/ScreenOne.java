package app.main.gui.screens;

import app.main.map.Graph;
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
    private JPanel panAffNodeSel = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();

    private mxGraphComponent panAffNoeuds;

    private JLabel nodeSelectedTxt = new JLabel("Node selected: ");
    public JLabel nodeSelected = new JLabel("No node selected");

    private JButton neigbours = new JButton("Neigbours");
    private JButton nodeByLink = new JButton("Node by link");

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


        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));


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
                if (cell != null) {
                    nodeSelected.setText(cell.getValue().toString());
                    // Select the cell
                    panAffNoeuds.getGraph().setSelectionCell(cell);
                }
            }
        });

        neigbours.addActionListener(e -> {
            if (nodeSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(null, "No node selected");
            } else {
                JOptionPane.showMessageDialog(null, "Neigbours of " + nodeSelected.getText() + ": " + graph.getNodeFromString(nodeSelected.getText()).getAllNeighbours());
            }
        });
        /* FAIRE LA FONCTION
        nodeByLink.addActionListener(e -> {
            if (nodeSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(null, "No node selected");
            } else {
                JOptionPane.showMessageDialog(null, "Node by link " + nodeSelected.getText() + ": " + graph.getNodeFromString(nodeSelected.getText()).getNodeByLink());
            }
        });

         */



        panAffNodeSel.add(nodeSelectedTxt);
        panAffNodeSel.add(nodeSelected);

        panActionNoeud.add(neigbours);
        panActionNoeud.add(nodeByLink);

        containerLeft.add(panAffNodeSel, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }

}
