package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Node;
import app.main.nodes.NodeType;
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

    private JPanel leftCorner = new JPanel();

    private JPanel panAffNodeSelected = new JPanel();
    private JPanel resetPan = new JPanel();
    private JPanel panActionNoeud = new JPanel();

    private JPanel panListeNoeud = new JPanel();
    private mxGraphComponent panAffNoeuds;

    private JLabel nodeOneSelectedTxt = new JLabel("Noeud un sélectionné : ");
    private JLabel nodeTwoSelectedTxt = new JLabel("Noeud deux sélectionné : ");
    private JLabel nodeOneSelected = new JLabel("Pas de noeud sélectionné");
    private JLabel nodeTwoSelected = new JLabel("Pas de noeud sélectionné");

    private Node nodeOne;
    private Node nodeTwo;
    private Node bestNode;

    private JButton reset = new JButton("Reset");


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


        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)tailleEcran.getHeight();
        int width = (int)tailleEcran.getWidth();


        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


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

        panAffNodeSelected.setLayout(new GridLayout(3, 2));
        panAffNodeSelected.setSize(0, 150);
        panAffNodeSelected.setPreferredSize(new Dimension(0, 150));
        panAffNodeSelected.add(nodeOneSelectedTxt);
        panAffNodeSelected.add(nodeOneSelected);
        panAffNodeSelected.add(nodeTwoSelectedTxt);
        panAffNodeSelected.add(nodeTwoSelected);

        resetPan.setSize(0, 46);
        resetPan.setPreferredSize(new Dimension(0, 46));
        resetPan.add(reset);

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
                    if (!e.isControlDown()) {
                        nodeOne = (Node) cell.getValue();
                        ScreenThree.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else if (e.isControlDown()) {
                        nodeTwo = (Node) cell.getValue();
                        ScreenThree.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
                else{
                    panAffNoeuds.getGraph().clearSelection();
                }
            }
        });

        gastronomique.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenThree.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if(this.nodeOne.getType() == NodeType.VILLE && this.nodeTwo.getType() == NodeType.VILLE){
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "GASTRONOMIQUE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus gastronomique !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        ouverte.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenThree.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if(this.nodeOne.getType() == NodeType.VILLE && this.nodeTwo.getType() == NodeType.VILLE){
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "OUVERTE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus ouverte !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        culturel.addActionListener(e -> {
            if (ScreenThree.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenThree.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else if(this.nodeOne.getType() == NodeType.VILLE && this.nodeTwo.getType() == NodeType.VILLE){
                bestNode = ScreenThree.this.graph.isBetterThan(nodeOne, nodeTwo, "CULTURELLE");
                JOptionPane.showMessageDialog(ScreenThree.this, bestNode + " est plus culturelle !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(ScreenThree.this, "Veuillez sélectionner deux noeuds de type Ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        reset.addActionListener(e -> {
            nodeOneSelected.setText("Pas de noeud sélectionné");
            nodeTwoSelected.setText("Pas de noeud sélectionné");
            nodeOne = null;
            nodeTwo = null;
            graphDisplay.resetColours();
        });


        leftCorner.add(panAffNodeSelected, BorderLayout.CENTER);
        leftCorner.add(resetPan, BorderLayout.SOUTH);

        containerLeft.add(leftCorner, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
