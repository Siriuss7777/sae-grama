package app.main.gui.screens;

import app.main.map.Graph;

import javax.swing.*;
import java.awt.*;

public class ScreenZero extends JPanel {
    JFrame f;

    Graph map;
    private JPanel contentPane = new JPanel();
    private JPanel containerLeft = new JPanel();
    private JPanel containerRight = new JPanel();
    private JPanel panAffGen = new JPanel();
    private JPanel panActionNoeud = new JPanel();
    private JPanel panListeNoeud = new JPanel();

    private JLabel nbrVille = new JLabel("Nombre de villes : ");
    private JLabel nbrRest = new JLabel("Nombre de restaurants : ");
    private JLabel nbrLoisir = new JLabel("Nombre de centres de loisir : ");
    private JLabel nbrNat = new JLabel("Nombre de nationales : ");
    private JLabel nbrAuto = new JLabel("Nombre d'autoroutes : ");
    private JLabel nbrDep = new JLabel("Nombre de départementales : ");

    public ScreenZero(JFrame f, Graph map) {
        super();
        this.f = f;
        this.map = map;
        constpan();
    }
    private void constpan() {

        this.setLayout(new BorderLayout());

        this.add(containerLeft, BorderLayout.WEST);
        this.add(containerRight, BorderLayout.CENTER);


        containerLeft.setSize(400, 0);
        containerLeft.setPreferredSize(new Dimension(400, 0));


        containerRight.setLayout(new BorderLayout());
        containerLeft.setLayout(new BorderLayout());

        panAffGen.setLayout(new GridLayout(6, 2));
        //panAffGen.setBackground(Color.RED);
        panAffGen.setBorder(BorderFactory.createEtchedBorder());
        panAffGen.setSize(0, 200);
        panAffGen.setPreferredSize(new Dimension(0, 200));

        //panActionNoeud.setBackground(Color.BLACK);
        panActionNoeud.setBorder(BorderFactory.createEtchedBorder());
        panActionNoeud.setSize(0, 200);
        panActionNoeud.setPreferredSize(new Dimension(0, 200));


        //panListeNoeud.setBackground(Color.BLUE);
        panListeNoeud.setBorder(BorderFactory.createEtchedBorder());


        System.out.println(map.getVillesCount());
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


        containerLeft.add(panAffGen, BorderLayout.NORTH);
        containerLeft.add(panListeNoeud, BorderLayout.CENTER);

        containerRight.add(panActionNoeud, BorderLayout.NORTH);

    }
}
