import com.mxgraph.swing.mxGraphComponent;
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

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        ListenableGraph<String, DefaultEdge> g =
                new DefaultListenableGraph<>(new DirectedWeightedPseudograph<>(DefaultEdge.class));


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

//        System.out.println(map.getNodeFromString("V,Paris").getNeighbourNodes());
//        for(Link link: map.getNodeFromString("V,Paris").getNeighbours()){
//            System.out.println(link.toString());
//        }

//        Display the graph
        JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);
        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);

        //Layout
        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.setForceConstant(250);
        layout.execute(jgxAdapter.getDefaultParent());

        //Display
        Window window = new Window();
        window.init();
        window.add(graphComponent);



        System.out.println(map.getPathWith(map.getNodeFromString("V,Paris"),map.getNodeFromString("V,Sevran"),map.getNodeFromString("V,Lyon"),map.getNodeFromString("R,Pizza Hut Bondy")));




    }
}