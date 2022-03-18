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
        while ((line = file.readLine()) != null){
            type = line.substring(line.indexOf(','));
            name = line.substring(line.indexOf(',')+1,line.indexOf(':'));
            currentNode = map.createNode(type, name);

            line = line.substring(line.indexOf(':')+1);
            links = line.split(";");

            for(String link: links){
                dividedLink = link.split("::");                   // dividedLink[0] -> type/distance du lien, dividedLink[1] -> type/nom du noeud
                if((newNode = map.getNodeFromString(dividedLink[1])) == null){
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

//    static Node lyon = map.createNode("V", "Lyon");
//    static Node paris = map.createNode("V", "Paris");
//    static Node vaulx = map.createNode("V", "Vaulx-en-Velin");
//    static Node mars = map.createNode("V", "Marseille");
//    static Node rouen = map.createNode("V", "Rouen");
//    static Node heis = map.createNode("L", "Heisenberg");
//    static Node resto = map.createNode("R", "Chez M. Restaurant ^^");

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.println("Saisissez le chemin vers la map (fichier CSV): ");
        String filename = input.nextLine();
        loadFile(filename);

        System.out.println(map);

//        map.linkNodes(lyon, paris, "A", 100);
//        map.linkNodes(lyon, vaulx, "D", 20);
//        map.linkNodes(vaulx, resto, "D", 5);
//        map.linkNodes(lyon, resto, "N", 20);
//        map.linkNodes(rouen, heis, "N", 5);
//        map.linkNodes(heis, lyon, "A", 20);
//        map.linkNodes(paris, mars, "A", 70);
//        map.linkNodes(paris, mars, "N", 20);
//        map.linkNodes(mars, vaulx, "N", 10);
//        map.linkNodes(mars, rouen, "A", 40);
//        map.linkNodes(paris, rouen, "D", 20);
//
//        //System.out.println(map);
//
//        Node node1 = rouen;
//        Node node2 = resto;
//        Node node3 = mars;
//        for(Node node: map.getShortestPath(node1, node2)){
//            System.out.println(node.getName());
//        }
//        System.out.println("Distance de : " + map.getShortestDistance(node1,node2));
//
//        System.out.println("----pathWith----");
//        for(Node node: map.getPathWith(node1, node2, node3)){
//            System.out.println(node.getName());
//        }
//
//        System.out.println(map.Distance(node1, node2, 3));
//
//        System.out.println("");
//        System.out.println(map.isBetterThan(node1,node2,"CULTURELLE"));
    }
}