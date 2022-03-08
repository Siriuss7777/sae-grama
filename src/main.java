import java.io.*;
import java.util.*;
public class main {
    static Map map = new Map();

    static Node lyon = new Node("V", "Lyon");
    static Node paris = new Node("V", "Paris");
    static Node vaulx = new Node("V", "Vaulx-en-Velin");
    static Node mars = new Node("V", "Marseille");
    static Node rouen = new Node("V", "Rouen");
    static Node heis = new Node("L", "Heisenberg");
    static Node resto = new Node("R", "Chez M. Restaurant ^^");


    static Link pla = new Link(lyon, paris, "A", 100);
    static Link lvd = new Link(lyon, vaulx, "D", 20);
    static Link vrd = new Link(vaulx, resto, "D", 5);
    static Link lvn = new Link(lyon, vaulx, "N", 40);
    static Link lrn = new Link(lyon, resto, "N", 15);
    static Link hrn = new Link(rouen, heis, "N", 5);
    static Link hla = new Link(heis, lyon, "A", 20);
    static Link pma = new Link(paris, mars, "A", 70);
    static Link mva = new Link(mars, vaulx, "N", 10);
    static Link roma = new Link(mars, rouen, "A", 40);
    static Link ropa = new Link(paris, rouen, "D", 20);


    private static void loadFile(BufferedReader reader){
        try{
            String line = reader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        map.addNode(lyon);
        map.addNode(paris);
        map.addNode(vaulx);
        map.addNode(mars);
        map.addNode(rouen);
        map.addNode(heis);
        map.addNode(resto);

        map.addLink(pla);
        map.addLink(lvd);
        map.addLink(vrd);
        map.addLink(lvn);
        map.addLink(lrn);
        map.addLink(hrn);
        map.addLink(hla);
        map.addLink(pma);
        map.addLink(mva);
        map.addLink(roma);
        map.addLink(ropa);

        System.out.println("\n\n\n\n");

        for(Node node: map.getShortestPath(vaulx, rouen)){
            System.out.println(node.getName());
        }
    }
}