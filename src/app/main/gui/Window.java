package app.main.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Member;


import app.main.gui.screens.*;
import app.main.map.*;
import com.formdev.flatlaf.*;


public class Window extends JFrame {
    private JFrame f;
    private JPanel containerPan = new JPanel();


    private Graph graph;

    public Window(Graph graph){
        super();
        this.graph = graph;
        this.f = this;
        init();
    }


    public void init() {
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch(Exception e){
            System.out.println("Erreur lors de l'utilisation du LaF");
        }
        setTitle("SAE GRAMA - Guenot/Le Gall");
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setContentPane(constrPan());
        add(constMenu());
        setVisible(true);
    }
/*
    private JPanel constrPan(){
        GraphDisplay gd = new GraphDisplay(graph);

        containerPan.setLayout(new BorderLayout());

        containerPan.add(new MainScreen(f, graph, gd), BorderLayout.CENTER);

        return containerPan;
    }

 */

    private JTabbedPane constMenu(){
        JTabbedPane menu = new JTabbedPane();
        GraphDisplay gd = new GraphDisplay(graph);

        JPanel mainTab = new MainScreen(f, graph, gd);
        menu.addTab("Écran principal", mainTab);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(menu.getSelectedIndex() == 0){
                    mainTab.removeAll();
                    mainTab.add(new MainScreen(f, graph, gd));
                }
            }
        });

        JPanel tab0 = new JPanel();
        menu.addTab("Écran 0", tab0);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(menu.getSelectedIndex() == 1){
                    tab0.removeAll();
                    tab0.add(new ScreenZero(f, graph, gd));
                }
            }
        });

        JPanel tab1 = new JPanel();
        menu.addTab("Écran 1", tab1);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(menu.getSelectedIndex() == 2){
                    tab1.removeAll();
                    tab1.add(new ScreenOne(f, graph, gd));
                }
            }
        });

        JPanel tab2 = new JPanel();
        menu.addTab("Écran 2", tab2);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(menu.getSelectedIndex() == 3){
                    tab2.removeAll();
                    tab2.add(new ScreenTwo(f, graph, gd));
                }
            }
        });

        JPanel tab3 = new JPanel();
        menu.addTab("Écran 3", tab3);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(menu.getSelectedIndex() == 4){
                    tab3.removeAll();
                    tab3.add(new ScreenThree(f, graph, gd));
                }
            }
        });

        JPanel tab4 = new JPanel();
        menu.addTab("Écran 4", tab4);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(menu.getSelectedIndex() == 5){
                    tab4.removeAll();
                    tab4.add(new ScreenFour(f, graph, gd));
                    tab4.revalidate();
                }
            }
        });

        return menu;
    }

}
