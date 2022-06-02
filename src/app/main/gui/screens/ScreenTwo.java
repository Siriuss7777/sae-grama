package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenTwo extends JPanel {
    JFrame f;

    Graph graph;

    private JPanel contentPane = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffNodeSelected = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();
    private mxGraphComponent panAffNoeuds;

    private JLabel nodeOneSelectedTxt = new JLabel("Node one selected: ");
    private JLabel nodeTwoSelectedTxt = new JLabel("Node two selected: ");
    private JLabel nodeOneSelected = new JLabel("No node selected");
    private JLabel nodeTwoSelected = new JLabel("No node selected");

    private Node nodeOne;
    private Node nodeTwo;

    private JComboBox<String> nodeSelectedComboBox = new JComboBox<>(new String[]{"Node one", "Node two"});

    private JButton _2distance = new JButton("Are they at 2 distance?");

    public ScreenTwo(JFrame f, Graph graph) {
        super();
        this.f = f;
        this.graph = graph;
        constpan();
    }

    public void constpan(){

        this.setLayout(new BorderLayout());

        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        panAffNodeSelected.setLayout(new GridLayout(3, 2));
        panAffNodeSelected.setBorder(BorderFactory.createEtchedBorder());
        panAffNodeSelected.setSize(0, 200);
        panAffNodeSelected.setPreferredSize(new Dimension(0, 200));

        panAffNodeSelected.add(nodeOneSelectedTxt);
        panAffNodeSelected.add(nodeOneSelected);
        panAffNodeSelected.add(nodeTwoSelectedTxt);
        panAffNodeSelected.add(nodeTwoSelected);
        panAffNodeSelected.add(nodeSelectedComboBox);


        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));

        panActionNoeud.add(_2distance);


        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        GraphDisplay gd = new GraphDisplay(graph);
        panAffNoeuds = gd.initializeAffNoeuds();
        this.panAffNoeuds.getGraphControl().addMouseListener(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenTwo.this.panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null) {
                    if (ScreenTwo.this.nodeSelectedComboBox.getSelectedItem().equals("Node one")) {
                        nodeOne = (Node) cell.getValue();
                        ScreenTwo.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else {
                        nodeTwo = (Node) cell.getValue();
                        ScreenTwo.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
            }
        });

        _2distance.addActionListener(e -> {
            if (ScreenTwo.this.nodeOneSelected.getText().equals("No node selected") || ScreenTwo.this.nodeTwoSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(ScreenTwo.this, "Please select two nodes", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (ScreenTwo.this.graph.Distance(nodeOne, nodeTwo, 2)) {
                    JOptionPane.showMessageDialog(ScreenTwo.this, "Yes, they are at 2 distance", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(ScreenTwo.this, "No, they are not at 2 distance", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        containerLeft.add(panAffNodeSelected, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
