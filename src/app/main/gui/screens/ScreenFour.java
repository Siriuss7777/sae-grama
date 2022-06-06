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

    private JLabel nodeOneSelectedTxt = new JLabel("Noeud un sélectionné : ");
    private JLabel nodeTwoSelectedTxt = new JLabel("Noeud deux sélectionné : ");
    private JLabel nodeThreeSelectedTxt = new JLabel("Noeud un à traverser : ");
    private JLabel nodeFourSelectedTxt = new JLabel("Noeud deux à traverser : ");
    private JLabel nodeOneSelected = new JLabel("Pas de noeud sélectionné");
    private JLabel nodeTwoSelected = new JLabel("Pas de noeud sélectionné");
    private JLabel nodeThreeSelected = new JLabel("Pas de noeud sélectionné");
    private JLabel nodeFourSelected = new JLabel("Pas de noeud sélectionné");

    private Node nodeOne;
    private Node nodeTwo;
    private Node nodeThree;
    private Node nodeFour;

    private JButton floydWarshall = new JButton("Distance la plus courte entre les deux noeuds");
    private JButton nDistance = new JButton("Distance entre les deux noeuds");
    private JLabel labelDistance = new JLabel("Distance entre les deux noeuds à tester : ");
    private JComboBox<Integer> distance = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});

    private JButton pathWith = new JButton("Chemin traversant les deux noeuds");

    private JButton nDistanceMin = new JButton("Distance minimum entre les deux noeuds");

    private JComboBox<String> nodeSelectedComboBox = new JComboBox<>(new String[]{"Noeud un", "Noeud deux", "Noeud à traverser un", "Noeud à traverser deux"});

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

        panAffNodeSelected.setLayout(new GridLayout(5, 2));
        panAffNodeSelected.setBorder(BorderFactory.createEtchedBorder());
        panAffNodeSelected.setSize(0, 200);
        panAffNodeSelected.setPreferredSize(new Dimension(0, 200));

        panAffNodeSelected.add(nodeOneSelectedTxt);
        panAffNodeSelected.add(nodeOneSelected);
        panAffNodeSelected.add(nodeTwoSelectedTxt);
        panAffNodeSelected.add(nodeTwoSelected);
        panAffNodeSelected.add(nodeThreeSelectedTxt);
        panAffNodeSelected.add(nodeThreeSelected);
        panAffNodeSelected.add(nodeFourSelectedTxt);
        panAffNodeSelected.add(nodeFourSelected);
        panAffNodeSelected.add(new JLabel("Vous choisissez le noeud : "));
        panAffNodeSelected.add(nodeSelectedComboBox);



        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));

        panActionNoeud.add(floydWarshall);

        panActionNoeud.add(nDistance);
        panActionNoeud.add(distance);

        panActionNoeud.add(pathWith);

        panActionNoeud.add(nDistanceMin);



        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        panAffNoeuds = graphDisplay.initializeAffNoeuds(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenFour.this.panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (ScreenFour.this.nodeSelectedComboBox.getSelectedItem().equals("Noeud un")) {
                        nodeOne = (Node) cell.getValue();
                        ScreenFour.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else if (ScreenFour.this.nodeSelectedComboBox.getSelectedItem().equals("Noeud deux")) {
                        nodeTwo = (Node) cell.getValue();
                        ScreenFour.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                    else if (ScreenFour.this.nodeSelectedComboBox.getSelectedItem().equals("Noeud à traverser un")) {
                        nodeThree = (Node) cell.getValue();
                        ScreenFour.this.nodeThreeSelected.setText(nodeThree.getName());
                    }
                    else if (ScreenFour.this.nodeSelectedComboBox.getSelectedItem().equals("Noeud à traverser deux")) {
                        nodeFour = (Node) cell.getValue();
                        ScreenFour.this.nodeFourSelected.setText(nodeFour.getName());
                    }
                }
                else{
                    panAffNoeuds.getGraph().clearSelection();
                }
            }
        });

        floydWarshall.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "Distance la plus courte entre les deux noeuds: " + graph.getMatrix().lowestDistance(nodeOne,nodeTwo)+ ". Le chemin est : "+ graph.getMatrix().getShortestPath(nodeOne, nodeTwo), "Success", JOptionPane.INFORMATION_MESSAGE);

            }

        });

        nDistance.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if (graph.Distance(nodeOne,nodeTwo,distance.getSelectedIndex()+1)){
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance entre les deux noeuds est de : " + (distance.getSelectedIndex()+1), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance entre les deux noeuds n'est pas de : " + (distance.getSelectedIndex()+1), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        });

        pathWith.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeThreeSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner au moins 3 noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if (nodeFourSelected.getText().equals("Pas de noeud sélectionné")){
                JOptionPane.showMessageDialog(ScreenFour.this, "Le chemin traversant le noeuds est : " + graph.getPathWith(nodeOne,nodeTwo,nodeThree), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "Le chemin traversant les deux noeuds est : " + graph.getPathWith(nodeOne,nodeTwo,nodeThree,nodeFour), "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        nDistanceMin.addActionListener(e -> {
            if (ScreenFour.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenFour.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenFour.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ScreenFour.this, "La distance minimum entre les deux noeuds est de : " + graph.DistanceMin(nodeOne,nodeTwo), "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        });




        containerLeft.add(panAffNodeSelected, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
