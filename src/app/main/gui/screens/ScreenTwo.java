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
    private JLabel nodeOneSelected = new JLabel("Pas de noeud sélectionné");
    private JLabel nodeTwoSelected = new JLabel("Pas de noeud sélectionné");

    private Node nodeOne;
    private Node nodeTwo;


    private JButton _2distance = new JButton("Sont-ils à 2 distance ?");

    public ScreenTwo(JFrame f, Graph graph, GraphDisplay graphDisplay) {
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
        int hauteur = (int)tailleEcran.getHeight();
        int largeur = (int)tailleEcran.getWidth();


        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400, hauteur);
        containerLeft.setPreferredSize(new Dimension(400, hauteur));

        int width = largeur - 400;

        containerRight.setSize(width, hauteur);
        containerRight.setPreferredSize(new Dimension(width, hauteur));


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


        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));

        panActionNoeud.add(_2distance);


        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());

        panAffNoeuds = graphDisplay.initializeAffNoeuds(new mxMouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mxCell cell = (mxCell) ScreenTwo.this.panAffNoeuds.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.isVertex()) {
                    if (!e.isControlDown()) {
                        panAffNoeuds.getGraph().setSelectionCell(cell);
                        nodeOne = (Node) cell.getValue();
                        ScreenTwo.this.nodeOneSelected.setText(nodeOne.getName());
                    }
                    else if (e.isControlDown()) {
                        panAffNoeuds.getGraph().setSelectionCell(cell);
                        nodeTwo = (Node) cell.getValue();
                        ScreenTwo.this.nodeTwoSelected.setText(nodeTwo.getName());
                    }
                }
                else{
                    panAffNoeuds.getGraph().clearSelection();
                }
            }
        });

        _2distance.addActionListener(e -> {
            if (ScreenTwo.this.nodeOneSelected.getText().equals("Pas de noeud sélectionné") || ScreenTwo.this.nodeTwoSelected.getText().equals("Pas de noeud sélectionné")) {
                JOptionPane.showMessageDialog(ScreenTwo.this, "Veuillez sélectionner deux noeuds", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (graph.Distance(nodeOne, nodeTwo, 2) == 2) {
                    JOptionPane.showMessageDialog(ScreenTwo.this, "Oui, ils sont à 2 distance", "Succès", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(ScreenTwo.this, "Non, ils ne sont pas à 2 distance", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        containerLeft.add(panAffNodeSelected, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);
    }
}
