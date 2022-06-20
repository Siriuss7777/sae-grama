package app.main.gui.screens;

import javax.swing.*;
import java.awt.*;

public class Key extends JPanel {
    //TODO : À redesigner
    private final Color blue = new Color(60, 100, 185);
    private final Color orange = new Color(252, 163, 17);
    private final Color red = new Color(255, 51, 51);

    public Key() {
        constPan();
    }

    private void constPan() {
        this.setLayout(new GridLayout(9,2));
        this.add(new JLabel("Légende"));
        this.add(new JLabel(""));
        this.add(new JLabel("Noeuds"));
        this.add(new JLabel(""));
        this.add(new JLabel("Noeud bleu : "));
        JLabel rest = new JLabel("Restaurant");
        rest.setForeground(blue);
        this.add(rest);
        this.add(new JLabel("Noeud orange : "));
        JLabel loisir = new JLabel("Centre de loisir");
        loisir.setForeground(orange);
        this.add(loisir);
        this.add(new JLabel("Noeud rouge : "));
        JLabel city = new JLabel("Ville");
        city.setForeground(red);
        this.add(city);
        this.add(new JLabel("Liens"));
        this.add(new JLabel(""));
        this.add(new JLabel("Lien bleu : "));
        JLabel dep = new JLabel("Departementale");
        dep.setForeground(blue);
        this.add(dep);;
        this.add(new JLabel("Lien orange : "));
        JLabel nat = new JLabel("Nationale");
        nat.setForeground(orange);
        this.add(nat);
        this.add(new JLabel("Lien rouge : "));
        JLabel aut = new JLabel("Autoroute");
        aut.setForeground(red);
        this.add(aut);
    }
}
