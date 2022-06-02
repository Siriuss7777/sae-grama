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

    public ScreenOne(JFrame f, Graph graph) {
        super();
        this.f = f;
        this.graph = graph;
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

        GraphDisplay gd = new GraphDisplay(graph);
        panAffNoeuds = gd.initializeAffNoeuds();
        this.panAffNoeuds.getGraphControl().addMouseListener(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenOne.this.panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null) {
                    ScreenOne.this.nodeSelected.setText(cell.getValue().toString());
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
