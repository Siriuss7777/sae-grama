package app.main.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;


import app.main.gui.screens.*;
import app.main.gui.utils.GraphDisplay;
import app.main.gui.screens.MainScreen;
import app.main.map.*;


public class Window extends JFrame {
    // TODO : Sélectionner le fichier dans un JMenuBar
    // TODO : Popup d'aide dans un JMenuBar aussi
    private final JFrame f;
    private final JPanel containerPan = new JPanel();


    private Graph graph;

    public Window() {

        super();

        this.f = this;

        showFilePromptAndInit();
        /* No need to call init() there, as showFilePromptAndInit actually does it */
    }


    public void init() {
        setTitle("SAE GRAMA - Guenot/Le Gall");
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setContentPane(constrPan());
        buildMenuBar();
        buildTabbedPane();
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(f, "Voulez-vous vraiment quitter ?", "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        /* ========== Menu Fichier ========== */

        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem loadMenuItem = new JMenuItem("Charger", UIManager.getIcon("FileView.directoryIcon"));

        loadMenuItem.addActionListener(e -> {
            this.showFilePromptAndInit();
        });

        fileMenu.add(loadMenuItem);

        /* ========== Menu Aide ========== */

        JMenu helpMenu = new JMenu("Aide");
        JMenuItem helpMenuItem = new JMenuItem("Raccourcis clavier");
        helpMenuItem.addActionListener(e ->

        {
            JOptionPane.showMessageDialog(f, "Raccourcis clavier :\n" +
                    "Zoom : Ctrl + molette de la souris\n" +
                    "Sélection du premier noeud : Clic gauche\n" +
                    "Sélection du second noeud : Ctrl + Clic gauche\n" +
                    "Sélection des noeuds secondaires (écran 4) : Shift + Clic gauche\n"
            );

        });

        JMenuItem aboutMenuItem = new JMenuItem("À propos");
        aboutMenuItem.addActionListener(e ->{
            JOptionPane.showMessageDialog(f,
                    "GRAMA est un logiciel de visualisation et d’analyse de graphes. Il aura pour but de permettre la visualisation et la manipulation de cartes sous forme d’un graphe.\n" +
                            "\nProjet réalisé par :\n         GUENOT Matéo\n          LE GALL Bastien\n" +
                    "\nVersion : 1.0\n" +
                    "© 2022 Tous droits réservés\n" + 
                    "Licences utilisées : FlatLaF, JGraphX, mxGraph");
        });




        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);

        /* ========== Menu Bar ========== */

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

    }

    private void buildTabbedPane() {
        JTabbedPane menu = new JTabbedPane();
        GraphDisplay gd = new GraphDisplay(graph);

        if(graph.getNodesCount() + graph.getLinksCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Une erreur est survenue lors du chargement du graphe, veuillez en charger un nouveau",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }

        JPanel mainTab = new MainScreen(f, graph, gd);
        menu.addTab("Écran principal", mainTab);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (menu.getSelectedIndex() == 0) {
                    mainTab.removeAll();
                    mainTab.add(new MainScreen(f, graph, gd));
                    mainTab.revalidate();
                }
            }
        });

        JPanel tab0 = new JPanel();
        menu.addTab("Écran 0", tab0);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (menu.getSelectedIndex() == 1) {
                    tab0.removeAll();
                    tab0.add(new ScreenZero(f, graph, gd));
                    tab0.revalidate();
                }
            }
        });

        JPanel tab1 = new JPanel();
        menu.addTab("Écran 1", tab1);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (menu.getSelectedIndex() == 2) {
                    tab1.removeAll();
                    tab1.add(new ScreenOne(f, graph, gd));
                    tab1.revalidate();
                }
            }
        });

        JPanel tab2 = new JPanel();
        menu.addTab("Écran 2", tab2);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (menu.getSelectedIndex() == 3) {
                    tab2.removeAll();
                    tab2.add(new ScreenTwo(f, graph, gd));
                    tab2.revalidate();
                }
            }
        });

        JPanel tab3 = new JPanel();
        menu.addTab("Écran 3", tab3);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (menu.getSelectedIndex() == 4) {
                    tab3.removeAll();
                    tab3.add(new ScreenThree(f, graph, gd));
                    tab3.revalidate();
                }
            }
        });

        JPanel tab4 = new JPanel();
        menu.addTab("Écran 4", tab4);
        menu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (menu.getSelectedIndex() == 5) {
                    tab4.removeAll();
                    tab4.add(new ScreenFour(f, graph, gd));
                    tab4.revalidate();
                }
            }
        });

        this.add(menu);
    }


    /**
     * Displays a JFileChooser to select a file to load.
     */
    private void showFilePromptAndInit() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier CSV", "csv"));
        int result = fileChooser.showOpenDialog(f);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                /* This is when the window is initialized */
                loadFile(String.valueOf(selectedFile));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Reloads the app to display the parameter file
     */
    public void loadFile(String filename) throws IOException {
        this.getContentPane().removeAll();
        this.graph = new Graph();
        graph.init(filename);
        init();
        this.getContentPane().revalidate();
    }

}
