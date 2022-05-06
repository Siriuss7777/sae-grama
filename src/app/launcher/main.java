package app.launcher;

import app.main.*;
import app.main.Map;

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



        String filename = null;

        filename = "src/resources/test.csv";
        loadFile(filename);


        System.out.println(map);

    }
}
