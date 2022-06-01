package app.main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


import app.main.map.*;
import app.main.nodes.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.layout.*;

import com.mxgraph.swing.util.mxMouseAdapter;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import org.jgrapht.ListenableGraph;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;




public class Window extends JFrame {
    private String title;
    private JPanel contentPane = new JPanel();
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

    private Map map;

    public Window(Map map){
        super();
        this.map = map;
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
        contentPane.setLayout(new BorderLayout());

        contentPane.add(containerLeft, BorderLayout.WEST);
        contentPane.add(containerRight, BorderLayout.CENTER);


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


        initializeAffNoeuds();
        //panAffNoeuds.setBackground(Color.YELLOW);
        panAffNoeuds.setBorder(BorderFactory.createEtchedBorder());


        panAffGen.add(nbrVille);
        panAffGen.add(new JLabel(String.valueOf(map.getVillesCount()))); // Recupere le nbr de ville et le met en String
        panAffGen.add(nbrRest);
        panAffGen.add(new JLabel(String.valueOf(map.getRestaurantsCount())));
        panAffGen.add(nbrLoisir);
        panAffGen.add(new JLabel(String.valueOf(map.getLoisirsCount())));
        panAffGen.add(nbrDep);
        panAffGen.add(new JLabel(String.valueOf(map.getDepartementalesCount())));
        panAffGen.add(nbrNat);
        panAffGen.add(new JLabel(String.valueOf(map.getNationalesCount())));
        panAffGen.add(nbrAuto);
        panAffGen.add(new JLabel(String.valueOf(map.getAutoroutesCount())));


        panActionNoeud.add(_2Distance);
        panActionNoeud.add(_nDistance);

        containerLeft.add(panAffGen, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);
        containerRight.add(panAffNoeuds, BorderLayout.CENTER);


        return contentPane;
    }

    private JMenuBar constMenu(){
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");

        JMenu distance_1 = new JMenu("1-Distance");
        JMenuItem allNeighbours = new JMenuItem("All neighbours");
        JMenuItem closestNeighbour = new JMenuItem("Closest neighbour");
        JMenuItem farthestNeighbour = new JMenuItem("Farthest neighbour");

        JMenu distance_2 = new JMenu("2-Distance");

        JMenu distance_n = new JMenu("2+-Distance");

        JMenu help = new JMenu("Help");

        //JMenuItem copy = new JMenuItem(new Copie("Copier", this));

        file.add(open);


        menubar.add(file);
        menubar.add(distance_1);
        menubar.add(distance_2);
        menubar.add(distance_n);
        menubar.add(help);
        return menubar;
    }

    private void initializeAffNoeuds(){
        ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(new DirectedWeightedPseudograph<>(DefaultEdge.class));

        for (Node node : map.getNodes()) {
            g.addVertex(node.toString());
        }

        for (Node node : map.getNodes()) {
            for (Link neighbour : node.getAllNeighbours()) {
                g.setEdgeWeight(g.addEdge(node.toString(),
                        neighbour.getNode().toString()), neighbour.getDistance());
            }
        }

        JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);
        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);
        graphComponent.setConnectable(false);
        graphComponent.setEnabled(false);



        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.setForceConstant(250);
        layout.execute(jgxAdapter.getDefaultParent());


        this.panAffNoeuds = graphComponent;
        for(Object cell: graphComponent.getGraph().getChildCells(graphComponent.getGraph().getDefaultParent())){
            System.out.println(((mxCell) cell).getValue() + " X: " + ((mxCell) cell).getGeometry().getX() + " Y: " + ((mxCell) cell).getGeometry().getY());
        }

        graphComponent.getGraphControl().addMouseListener(new mxMouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Check if the mouse is over a cell
                mxCell cell = ((mxCell) graphComponent.getCellAt(e.getX(), e.getY(), false));
                if (cell != null) {
                    //Check if the cell is a vertex
                    if (graphComponent.getGraph().getModel().isVertex(cell)) {
                        // TODO: Send the node to the right panel
                    }
                }
            }
        });
    }



}