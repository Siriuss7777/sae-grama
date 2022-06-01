package app.main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import app.main.gui.screens.*;
import app.main.map.*;
import app.main.nodes.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.layout.*;

import com.mxgraph.swing.util.mxMouseAdapter;
import com.mxgraph.model.mxCell;
import com.mxgraph.layout.*;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedWeightedPseudograph;


public class Window extends JFrame {
    private String title;
    private JFrame f;
    private JPanel containerPan = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffGen = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();
    private mxGraphComponent panAffNoeuds;

    private JButton _2Distance = new JButton("2-Distance");
    private JButton _nDistance = new JButton("n-Distance");

    private JLabel nbrVille = new JLabel("Nombre de villes : ");
    private JLabel nbrRest = new JLabel("Nombre de restaurants : ");
    private JLabel nbrLoisir = new JLabel("Nombre de centres de loisir : ");
    private JLabel nbrNat = new JLabel("Nombre de nationales : ");
    private JLabel nbrAuto = new JLabel("Nombre d'autoroutes : ");
    private JLabel nbrDep = new JLabel("Nombre de départementales : ");

    private Graph graph;

    public Window(Graph graph){
        super();
        this.graph = graph;
        this.f = this;
        this.graph = graph;
        init();
    }


    public void init() {
        setTitle("SAE GRAMA - Guenot/Le Gall");
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(constrPan());
        setJMenuBar(constMenu());
        setVisible(true);
    }

    private JPanel constrPan(){
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        containerPan.setLayout(new BorderLayout());

        containerPan.add(containerLeft, BorderLayout.WEST);
        containerPan.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400,0);
        containerLeft.setPreferredSize(new Dimension(400,0));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        panAffGen.setLayout(new GridLayout(6,2));
        //panAffGen.setBackground(Color.RED);
        panAffGen.setBorder(BorderFactory.createEtchedBorder());
        panAffGen.setSize(0,200);
        panAffGen.setPreferredSize(new Dimension(0,200));

        //panActionNoeud.setBackground(Color.BLACK);
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0,200);
        panActionNoeud.setPreferredSize(new Dimension(0,200));


        //panListeNoeud.setBackground(Color.BLUE);
        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());


        //panAffNoeuds.setBackground(Color.YELLOW);
        GraphDisplay gd = new GraphDisplay(graph);
        panAffNoeuds = gd.initializeAffNoeuds();
        panAffNoeuds.setBorder(BorderFactory.createEtchedBorder());


        panAffGen.add(nbrVille);
        panAffGen.add(new JLabel(String.valueOf(graph.getVillesCount()))); // Recupere le nbr de ville et le met en String
        panAffGen.add(nbrRest);
        panAffGen.add(new JLabel(String.valueOf(graph.getRestaurantsCount())));
        panAffGen.add(nbrLoisir);
        panAffGen.add(new JLabel(String.valueOf(graph.getLoisirsCount())));
        panAffGen.add(nbrDep);
        panAffGen.add(new JLabel(String.valueOf(graph.getDepartementalesCount())));
        panAffGen.add(nbrNat);
        panAffGen.add(new JLabel(String.valueOf(graph.getNationalesCount())));
        panAffGen.add(nbrAuto);
        panAffGen.add(new JLabel(String.valueOf(graph.getAutoroutesCount())));



        panActionNoeud.add(_2Distance);
        panActionNoeud.add(_nDistance);

        containerLeft.add(panAffGen, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);


        return containerPan;
    }

    private JMenuBar constMenu(){
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");

        // Ecran Principale
        JMenu mainScreen = new JMenu("Ecran principale");
        JMenuItem main = new JMenuItem("Ecran principale");
        mainScreen.add(main);
        main.addActionListener(e -> {
            f.getContentPane().removeAll();
            f.getContentPane().add(new MainScreen(f));
            f.revalidate();
        });


        // Ecran 0
        JMenu screen_0 = new JMenu("Ecran 0");
        JMenuItem item_0 = new JMenuItem("Ecran 0");
        screen_0.add(item_0);
        item_0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.getContentPane().removeAll();
                f.getContentPane().add(new ScreenZero(f, graph));
                f.revalidate();
            }
        });


        // Ecran 1
        JMenu screen_1 = new JMenu("Ecran 1");
        JMenuItem item_1 = new JMenuItem("Ecran 1");
        screen_1.add(item_1);
        item_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.getContentPane().removeAll();
                f.getContentPane().add(new ScreenOne(f));
                f.revalidate();
            }
        });


        // Ecran 2
        JMenu screen_2 = new JMenu("Ecran 2");
        JMenuItem item_2 = new JMenuItem("Ecran 2");
        screen_2.add(item_2);
        item_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.getContentPane().removeAll();
                f.getContentPane().add(new ScreenTwo(f));
                f.revalidate();
            }
        });


        // Ecran 3
        JMenu screen_3 = new JMenu("Ecran 3");
        JMenuItem item_3 = new JMenuItem("Ecran 3");
        screen_3.add(item_3);
        item_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.getContentPane().removeAll();
                f.getContentPane().add(new ScreenThree(f));
                f.revalidate();
            }
        });


        // Ecran 4
        JMenu screen_4 = new JMenu("Ecran 4");
        JMenuItem item_4 = new JMenuItem("Ecran 4");
        screen_4.add(item_4);
        item_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.getContentPane().removeAll();
                f.getContentPane().add(new ScreenFour(f));
                f.revalidate();
            }
        });

        JMenu help = new JMenu("Help");

        //JMenuItem copy = new JMenuItem(new Copie("Copier", this));

        file.add(open);


        menubar.add(file);
        menubar.add(mainScreen);
        menubar.add(screen_0);
        menubar.add(screen_1);
        menubar.add(screen_2);
        menubar.add(screen_3);
        menubar.add(screen_4);
        menubar.add(help);


        return menubar;
    }

}
