package app.main.gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private String title;
    private JPanel contentPane = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffGen = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();
    private JPanel panAffNoeuds = new JPanel();

    private JButton _2Distance = new JButton("2-Distance");
    private JButton _nDistance = new JButton("n-Distance");

    private JLabel nbrVille = new JLabel("Nombre de ville : ");
    private JLabel nbrRest = new JLabel("Nombre de restaurant : ");
    private JLabel nbrLoisir = new JLabel("Nombre de centre de loisir : ");
    private JLabel nbrNat = new JLabel("Nombre de nationale : ");
    private JLabel nbrAuto = new JLabel("Nombre d'autoroute : ");
    private JLabel nbrDep = new JLabel("Nombre de departementale : ");

    public Window(){
        super();
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


        containerLeft.setSize(width/5,height);
        containerLeft.setPreferredSize(new Dimension(300,height));

        containerRight.setSize(0,height);
        containerRight.setPreferredSize(new Dimension(0,height));

        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        panAffGen.setLayout(new GridLayout(6,2));
        panAffGen.setBackground(Color.RED);
        panAffGen.setBorder(BorderFactory.createEtchedBorder());
        panAffGen.setSize(0,200);
        panAffGen.setPreferredSize(new Dimension(0,200));

        panActionNoeud.setBackground(Color.BLACK);
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0,200);
        panActionNoeud.setPreferredSize(new Dimension(0,200));


        panListeNoeud.setBackground(Color.BLUE);
        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());


        panAffNoeuds.setBackground(Color.YELLOW);
        panAffNoeuds.setBorder(BorderFactory.createEtchedBorder());


        panAffGen.add(nbrVille);
        panAffGen.add(new JLabel("12"));
        panAffGen.add(nbrRest);
        panAffGen.add(new JLabel("12"));
        panAffGen.add(nbrLoisir);
        panAffGen.add(new JLabel("12"));
        panAffGen.add(nbrDep);
        panAffGen.add(new JLabel("12"));
        panAffGen.add(nbrNat);
        panAffGen.add(new JLabel("12"));
        panAffGen.add(nbrAuto);
        panAffGen.add(new JLabel("12"));


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


}
