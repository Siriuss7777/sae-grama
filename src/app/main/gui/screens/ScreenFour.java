package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenFour extends JPanel {
    JFrame f;

    Graph graph;
    GraphDisplay graphDisplay;

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

    private JButton floydWarshall = new JButton("Distance la plus courte entre les deux noeuds");
    private JButton nDistance = new JButton("Distance entre les deux noeuds");
    private JLabel labelDistance = new JLabel("Distance entre les deux noeuds à tester : ");
    private JTextField distance = new JTextField("0");
    private int n = 0;

    private JComboBox<String> nodeSelectedComboBox = new JComboBox<>(new String[]{"Node one", "Node two"});

    public ScreenFour(JFrame f, Graph graph, GraphDisplay graphDisplay) {
        super();
        this.f = f;
        this.graph = graph;
        this.graphDisplay = graphDisplay;
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
        panActionNoeud.add(floydWarshall);
        panActionNoeud.add(nDistance);
        panActionNoeud.add(distance);



        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        panAffNoeuds = graphDisplay.initializeAffNoeuds(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenFour.this.panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (ScreenFour.this.nodeSelectedComboBox.getSelectedItem().equals("Node one")) {
                        nodeOne = (Node) cell.getValue();
                        ScreenFour.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else {
                        nodeTwo = (Node) cell.getValue();
                        ScreenFour.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
            }
        });

        floydWarshall.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("No node selected") || ScreenFour.this.nodeTwoSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "Distance la plus courte entre les deux noeuds: " + graph.getMatrix().lowestDistance(nodeOne,nodeTwo)+ ". Le chemin est : "+ graph.getMatrix().getShortestPath(nodeOne, nodeTwo), "Success", JOptionPane.INFORMATION_MESSAGE);

            }

        });

        // La fonction Distance ne fonctionne pas
        nDistance.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("No node selected") || ScreenFour.this.nodeTwoSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "Distance entre les deux noeuds: " + graph.Distance(nodeOne,nodeTwo,Integer.parseInt(distance.getText())), "Success", JOptionPane.INFORMATION_MESSAGE);

            }

        });


        containerLeft.add(panAffNodeSelected, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
