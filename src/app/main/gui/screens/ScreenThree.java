package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenThree extends JPanel {
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
    private Node returnNode;

    private JComboBox<String> nodeSelectedComboBox = new JComboBox<>(new String[]{"Node one", "Node two"});

    private JButton gastronomique = new JButton("Gastronomique");
    private JButton ouverte = new JButton("Ouverte");
    private JButton culturel = new JButton("Culturel");

    public ScreenThree(JFrame f, Graph graph, GraphDisplay graphDisplay) {
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

        panActionNoeud.setLayout(new FlowLayout());
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));
        panActionNoeud.add(gastronomique);
        panActionNoeud.add(ouverte);
        panActionNoeud.add(culturel);



        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        panAffNoeuds = graphDisplay.initializeAffNoeuds(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenThree.this.panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (ScreenThree.this.nodeSelectedComboBox.getSelectedItem().equals("Node one")) {
                        nodeOne = (Node) cell.getValue();
                        ScreenThree.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else {
                        nodeTwo = (Node) cell.getValue();
                        ScreenThree.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
            }
        });

        gastronomique.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("No node selected") || ScreenThree.this.nodeTwoSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                returnNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "GASTRONOMIQUE");
                JOptionPane.showMessageDialog(ScreenThree.this, returnNode + " est plus gastronomique !", "Succès", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        ouverte.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("No node selected") || ScreenThree.this.nodeTwoSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                returnNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "OUVERTE");
                JOptionPane.showMessageDialog(ScreenThree.this, returnNode + " est plus ouverte !", "Succès", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        culturel.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("No node selected") || ScreenThree.this.nodeTwoSelected.getText().equals("No node selected")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                returnNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "CULTURELLE");
                JOptionPane.showMessageDialog(ScreenThree.this, returnNode + " est plus culturelle !", "Succès", JOptionPane.INFORMATION_MESSAGE);

            }
        });


        containerLeft.add(panAffNodeSelected, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
