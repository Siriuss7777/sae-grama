import java.util.LinkedList;

public class Node {
    private String type; /*"V" Ville,"R" Restaurant,"L" Centre de loisirs*/
    private String name;
    private int distance = INFINITE; /* CHANGE FOR MAP -> Distance() */
    private LinkedList<Link> neighbours;
    private LinkedList<Node> shortestPath = new LinkedList<>();
    private static final int INFINITE = 100000;

    public Node(String type, String name) {
        this.type = type;
        this.name = name;
        this.neighbours = new LinkedList<Link>();
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getDistance(){ return this.distance; }
    public void setDistance(int distance){ this.distance = distance; }

    public void addNeighbour(Link link){
        this.neighbours.add(link);
    }

    public void addNeighbour(Node node, String type, int distance){
        this.neighbours.add(new Link(node, type, distance));
    }

    public LinkedList<Link> getNeighbours() { return neighbours; }

    public LinkedList<Node> getNeighbourNodes(){
        LinkedList<Node> returnedList = new LinkedList<>();
        for (Link link: this.getNeighbours()){
            returnedList.add(link.getNode());
        }
        return returnedList;
    }

    public LinkedList<Link> getNeighbour(Node node){ // Dijkstra, retourne le(s) lien(s) entre les deux noeuds
        LinkedList<Link> returnedList = new LinkedList<>();
        for(Link link: this.neighbours){
            if(link.getNode() == node){
                returnedList.add(link);
            }
        }

        return returnedList;
    }

    public Link getClosestNeighbour(Node node){ // Dijkstra, retourne le lien le plus court entre les deux noeuds

        LinkedList<Link> neighboursList = this.getNeighbour(node);
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

    public boolean isNeighbour(Node node){
        boolean state = false;
        for(Link neighbour: neighbours){
            if(node.toString().equals(neighbour.getNode().toString())){
                state = true;
            }
        }
        return state;
    }

    public Link addLink(Node node, String type, int length){
        Link tmpLink = new Link(node, type, length);
        neighbours.add(tmpLink);
        return tmpLink;
    }
    public void addLink(Link link){
        neighbours.add(link);
    }

    public String toString(){ return this.type + "," + this.name; }


    public LinkedList<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
