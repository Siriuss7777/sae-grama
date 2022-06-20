package app.main.nodes;

import java.util.LinkedList;

/**
 * @author Matéo Guenot, Bastien Le Gall
 */

public class Node {
    private int id;
    private static int id_increment;
    private NodeType type; /*"V" Ville,"R" Restaurant,"L" Centre de loisirs */
    private String name;
    private int distance = INFINITE; /* CHANGE FOR MAP -> Distance() */
    private final LinkedList<Link> neighbours;
    private static final int INFINITE = 100000;


    /**
     * @param type : Ville, Restaurant, Centre de loisirs
     * @param name : Nom de la ville, du restaurant, du centre de loisirs
     */
    public Node(NodeType type, String name) {
        this.type = type;
        this.name = name;
        this.neighbours = new LinkedList<Link>();
        this.id = id_increment++;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void addNeighbour(Link link) {
        this.neighbours.add(link);
    }

    public void addNeighbour(Node node, LinkType type, int distance) {
        this.neighbours.add(new Link(node, type, distance));
    }

    public LinkedList<Link> getAllNeighbours() {
        return neighbours;
    }

    /**
     * @return list of neighbours (node)
     */
    public LinkedList<Node> getNeighboursAsNodes() {
        LinkedList<Node> returnedList = new LinkedList<>();
        for (Link link : this.getAllNeighbours()) {
            returnedList.add(link.getNode());
        }
        return returnedList;
    }


    /**
     * @param node : Node to compare
     * @return list of links to node
     */
    public LinkedList<Link> getNeighbourLinksWithNode(Node node) {
        LinkedList<Link> returnedList = new LinkedList<>();
        for (Link link : this.neighbours) {
            if (link.getNode() == node) {
                returnedList.add(link);
            }
        }

        return returnedList;
    }

    /**
     * @param node : Node to compare
     * @return closest link to node
     */
    public Link getClosestNeighbour(Node node) { // Dijkstra, retourne le lien le plus court entre les deux noeuds

        LinkedList<Link> neighboursList = this.getNeighbourLinksWithNode(node);
        if (neighboursList.isEmpty()) {
            return null;
        }
        Link minLink = neighboursList.element();

        if (neighboursList.size() != 1) {
            for (Link link : neighboursList) {
                if (link.getDistance() < minLink.getDistance()) {
                    minLink = link;
                }
            }
        }
        return minLink;
    }

    /**
     * @param node : Node to compare
     * @return farthest link to node
     */
    public Link getFarthestNeighbour(Node node) { // Dijkstra, retourne le lien le plus court entre les deux noeuds

        LinkedList<Link> neighboursList = this.getNeighbourLinksWithNode(node);
        Link maxLink = neighboursList.element();

        if (neighboursList.size() != 1) {
            for (Link link : neighboursList) {
                if (link.getDistance() > maxLink.getDistance()) {
                    maxLink = link;
                }
            }
        }
        return maxLink;
    }

    /**
     * @param node : Node to compare
     * @return true if node is neighbour
     */
    public boolean isNeighbour(Node node) { // Dijkstra, retourne true si le noeud est voisin
        boolean state = false;
        for (Link neighbour : neighbours) {
            if (node.toString().equals(neighbour.getNode().toString())) {
                state = true;
            }
        }
        return state;
    }

    public Link addLink(Node node, LinkType type, int length) { // Ajoute un lien entre deux noeuds
        Link tmpLink = new Link(node, type, length, this);
        neighbours.add(tmpLink);
        return tmpLink;
    }

    public void addLink(Link link) {
        neighbours.add(link);
    }

    public String toString() {
        return this.type + "," + this.name;
    }


    public int getId() {
        return this.id;
    }
}
