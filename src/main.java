import java.io.*;
import java.util.*;
public class main {
    static Map map = new Map();

    static Node lyon = map.createNode("V", "Lyon");
    static Node paris = map.createNode("V", "Paris");
    static Node vaulx = map.createNode("V", "Vaulx-en-Velin");
    static Node mars = map.createNode("V", "Marseille");
    static Node rouen = map.createNode("V", "Rouen");
    static Node heis = map.createNode("L", "Heisenberg");
    static Node resto = map.createNode("R", "Chez M. Restaurant ^^");

    private static void loadFile(BufferedReader reader){
        try{
            String line = reader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        map.linkNodes(lyon, paris, "A", 100);
        map.linkNodes(lyon, vaulx, "D", 20);
        map.linkNodes(vaulx, resto, "D", 5);
        //map.linkNodes(lyon, resto, "N", 20);
        map.linkNodes(rouen, heis, "N", 5);
        map.linkNodes(heis, lyon, "A", 20);
        map.linkNodes(paris, mars, "A", 70);
        map.linkNodes(paris, mars, "N", 20);
        map.linkNodes(mars, vaulx, "N", 10);
        map.linkNodes(mars, rouen, "A", 40);
        map.linkNodes(paris, rouen, "D", 20);

        //System.out.println(map);

        Node node1 = rouen;
        Node node2 = mars;
        for(Node node: map.getShortestPath(node1, node2)){
            System.out.println(node.getName());
        }
        System.out.println("Distance de : " + map.getShortestDistance(node1,node2));

        System.out.println(map.Distance(node1, node2, 1));
    }
}