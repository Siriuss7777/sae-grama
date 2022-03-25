import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;
import jdk.swing.interop.SwingInterOpUtils;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;
import com.mxgraph.layout.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class main {

    private static void loadFile(String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String line, name, type;
        String[] links, dividedLink, attributes;
        Node currentNode;
        Node newNode;
        Link addedLink;
        while ((line = file.readLine()) != null) {
            type = line.substring(0, line.indexOf(','));
            name = line.substring(line.indexOf(',') + 1, line.indexOf(':'));
            currentNode = map.createNode(type, name);

            line = line.substring(line.indexOf(':') + 1);
            links = line.split(";");

            for (String link : links) {
                dividedLink = link.split("::");                   // dividedLink[0] -> type/distance du lien, dividedLink[1] -> type/nom du noeud
                if ((newNode = map.getNodeFromString(dividedLink[1])) == null) {
                    attributes = dividedLink[1].split(",");       // attributes[0] -> type, attributes[1] -> name  => Node
                    newNode = map.createNode(attributes[0], attributes[1]);
                }
                attributes = dividedLink[0].split(",");           // attributes[0] -> type, attributes[1] -> distance => Link
                addedLink = currentNode.addLink(newNode, attributes[0], Integer.parseInt(attributes[1]));
                map.addLink(addedLink);
            }
        }



    }


    static Map map = new Map();

    private static final Dimension DEFAULT_SIZE = new Dimension(900, 900);


    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        ListenableGraph<String, DefaultEdge> g =
                new DefaultListenableGraph<>(new DirectedWeightedPseudograph<>(DefaultEdge.class));
        JApplet applet = new JApplet();


//        Graphviz.fromGraph(g).width(200).render(Format.PNG).toFile(new File("example/ex1m.png"));
        System.out.println("Saisissez le chemin vers la map (fichier CSV): ");
//        String filename = input.nextLine();
        String filename = "C:\\Users\\Administrateur\\Desktop\\Cours\\SAEs\\GRAMA\\map.csv";
        loadFile(filename);

        System.out.println(map);

        for(Node node: map.getNodes()){
            g.addVertex(node.asString());
        }
        for(Node node: map.getNodes()) {
            for (Link neighbour : node.getNeighbours()) {
                g.setEdgeWeight(g.addEdge(node.asString(),
                        neighbour.getNode().asString()), neighbour.getDistance());
            }
        }

        System.out.println(map.getNodeFromString("V,Paris").getNeighbours());
        System.out.println(map.getPathWith(map.getNodeFromString("V,Paris"),map.getNodeFromString("V,Sevran"),map.getNodeFromString("V,Lyon"),map.getNodeFromString("V,Pizza Hut Bondy")));

//        //Display the graph
//        JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);
//        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);
//        graphComponent.setPreferredSize(DEFAULT_SIZE);
//        applet.getContentPane().add(graphComponent);
//        applet.setVisible(true);
//
//        //Layout
//        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
//        layout.setForceConstant(250);
//        layout.execute(jgxAdapter.getDefaultParent());
//
//        // Add the graph to the jframe
//        JFrame frame = new JFrame("JGraphT Adapter to JGraph Demo");
//        frame.getContentPane().add(graphComponent);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//        // Display jgraph




    }
}