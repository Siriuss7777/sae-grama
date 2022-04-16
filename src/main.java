import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;
import com.mxgraph.layout.*;

import javax.swing.*;
import java.io.*;
import java.util.*;

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
            currentNode = map.createNode(type.toUpperCase(Locale.ROOT), name);

            line = line.substring(line.indexOf(':') + 1);
            links = line.split(";");

            for (String link : links) {
                dividedLink = link.split("::");                   // dividedLink[0] -> type/distance du lien, dividedLink[1] -> type/nom du noeud
                attributes = dividedLink[1].split(",");       // attributes[0] -> type, attributes[1] -> name  => Node
                if ((newNode = map.getNodeFromString(attributes[0].toUpperCase(Locale.ROOT) + "," + attributes[1])) == null) {
                    newNode = map.createNode(attributes[0].toUpperCase(Locale.ROOT), attributes[1]);
                }
                attributes = dividedLink[0].split(",");           // attributes[0] -> type, attributes[1] -> distance => Link
                addedLink = currentNode.addLink(newNode, attributes[0].toUpperCase(Locale.ROOT), Integer.parseInt(attributes[1]));
                map.addLink(addedLink);
            }
        }
        map.floydWarshall();
    }

    static Map map = new Map();

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(new DirectedWeightedPseudograph<>(DefaultEdge.class));


//        String filename = null;
//        JFileChooser chooser = new JFileChooser();
//        int returnVal = chooser.showOpenDialog(null);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            filename = chooser.getSelectedFile().getAbsolutePath();
//            System.out.println("Vous avez choisi d'ouvrir ce fichier: " +
//                    chooser.getSelectedFile().getName());
//        }

        String filename = "C:\\Users\\Administrateur\\Desktop\\Cours\\SAEs\\GRAMA\\test.csv";
        loadFile(filename);


        System.out.println(map);

        for (Node node : map.getNodes()) {
            g.addVertex(node.toString());
        }
        for (Node node : map.getNodes()) {
            for (Link neighbour : node.getNeighbours()) {
                g.setEdgeWeight(g.addEdge(node.toString(),
                        neighbour.getNode().toString()), neighbour.getDistance());
            }
        }

//        Display the graph
        JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);
        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);

        //Layout
        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.setForceConstant(250);
        layout.execute(jgxAdapter.getDefaultParent());

        //Display
        Window window = new Window();
        window.add(graphComponent);
    }
}