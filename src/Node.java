import java.util.LinkedList;

public class Node {
    private String type; /*"ville", "restaurant", "centre de loisir"*/
    private String name;
    private int distance;
    private boolean found;
    private LinkedList<Link> neighbours;
    private LinkedList<Node> shortestPath = new LinkedList<>();
    private static int INFINITE = 100000;

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

    public boolean isFound(){ return this.found; }
    public void setFound(boolean found){ this.found = found; }

    public LinkedList<Link> getNeighbours() { return neighbours; }

    public LinkedList<Node> getNeighbourNodes(){
        LinkedList<Node> returnedList = new LinkedList<>();
        for (Link link: this.getNeighbours()){
            returnedList.add(link.getNode());
        }
        return returnedList;
    }

    public LinkedList<Link> getNeighbour(Node node){
        LinkedList<Link> returnedList = new LinkedList<>();
        for(Link link: this.neighbours){
            if(link.getNode() == node){
                returnedList.add(link);
            }
        }

        return returnedList;
    }

    public Link getClosestNeighbour(Node node){

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

    public Link addLink(Node node, String type, int length){
        Link tmpLink = new Link(node, type, length);
        neighbours.add(tmpLink);
        return tmpLink;
    }
    public void addLink(Link link){
        neighbours.add(link);
    }

    public String toString(){
        return this.name;
    }


    public LinkedList<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
