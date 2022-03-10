import java.util.LinkedList;

public class Node {
    private String type; /*"ville", "restaurant", "centre de loisir"*/
    private String name;
    private int distance;
    private Node predecessor;
    private LinkedList<Link> neighbours;

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

    public Node getPredecessor(){ return this.predecessor; }
    public void setPredecessor(Node predecessor){ this.predecessor = predecessor; }



    public LinkedList<Link> getNeighbours() { return neighbours; }

    public LinkedList<Node> getNeighbourNodes(){
        LinkedList<Node> returnedList = new LinkedList<>();
        for (Link link: this.getNeighbours()){
            returnedList.add(link.getNode());
        }
        return returnedList;
    }

    public Link getNeighbour(Node node){
        for(Link link: this.neighbours){
            if(link.getNode() == node){
                return link;
            }
        }
        return null;
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


}
