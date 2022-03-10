import java.util.LinkedList;

public class Node {
    private String type; /*"ville", "restaurant", "centre de loisir"*/
    private String name;
    private boolean found = false;
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

    public boolean isFound() { return found; }

    public LinkedList<Link> getNeighbours() { return neighbours; }

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
