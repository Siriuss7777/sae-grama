package app.main.nodes;

import java.util.LinkedList;

public class Node {
    private int id;
    private static int id_increment;
    private NodeType type; /*"V" Ville,"R" Restaurant,"L" Centre de loisirs */
    private String name;
    private int distance = INFINITE; /* CHANGE FOR MAP -> Distance() */
    private LinkedList<Link> neighbours;
    private static final int INFINITE = 100000;


    public Node(NodeType type, String name) {
        this.type = type;
        this.name = name;
        this.neighbours = new LinkedList<Link>();
        this.id = id_increment++;
    }

    public NodeType getType() { return type; }

    public void setType(NodeType type) { this.type = type; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getDistance(){ return this.distance; }
    public void setDistance(int distance){ this.distance = distance; }

    public void addNeighbour(Link link){
        this.neighbours.add(link);
    }

    public void addNeighbour(Node node, LinkType type, int distance){
        this.neighbours.add(new Link(node, type, distance));
    }

    public LinkedList<Link> getAllNeighbours() { return neighbours; }

    public LinkedList<Node> getNeighboursAsNodes(){
        LinkedList<Node> returnedList = new LinkedList<>();
        for (Link link: this.getAllNeighbours()){
            returnedList.add(link.getNode());
        }
        return returnedList;
    }



    public LinkedList<Link> getNeighbourLinksWithNode(Node node){ // Dijkstra, retourne le(s) lien(s) entre les deux noeuds
        LinkedList<Link> returnedList = new LinkedList<>();
        for(Link link: this.neighbours){
            if(link.getNode() == node){
                returnedList.add(link);
            }
        }

        return returnedList;
    }

    public Link getClosestNeighbour(Node node){ // Dijkstra, retourne le lien le plus court entre les deux noeuds

        LinkedList<Link> neighboursList = this.getNeighbourLinksWithNode(node);
        Link minLink = neighboursList.element();

        if(neighboursList.size()!=1){
            for(Link link: neighboursList){
                if (link.getDistance() < minLink.getDistance()){
                    minLink = link;
                }
            }
        }
        return minLink;
    }

    public Link getFarthestNeighbour(Node node){ // Dijkstra, retourne le lien le plus court entre les deux noeuds

        LinkedList<Link> neighboursList = this.getNeighbourLinksWithNode(node);
        Link maxLink = neighboursList.element();

        if(neighboursList.size()!=1){
            for(Link link: neighboursList){
                if (link.getDistance() > maxLink.getDistance()){
                    maxLink = link;
                }
            }
        }
        return maxLink;
    }

    public boolean isNeighbour(Node node){ // Dijkstra, retourne true si le noeud est voisin
        boolean state = false;
        for(Link neighbour: neighbours){
            if(node.toString().equals(neighbour.getNode().toString())){
                state = true;
            }
        }
        return state;
    }

    public Link addLink(Node node, LinkType type, int length){ // Ajoute un lien entre deux noeuds
        Link tmpLink = new Link(node, type, length);
        neighbours.add(tmpLink);
        return tmpLink;
    }
    public void addLink(Link link){
        neighbours.add(link);
    }

    public String toString(){ return this.type + "," + this.name; }


    public int getId(){
        return this.id;
    }
}
