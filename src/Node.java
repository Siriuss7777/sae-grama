import java.util.LinkedList;

public class Node {
    private String type; /*"ville", "restaurant", "centre de loisir"*/
    private String name;
    private int distance;
    private Node predecessor;
    private boolean found = false;

    public Node(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isFound() { return found; }

    public int getDistance() { return this.distance; }
    public void setDistance(int distance) { this.distance = distance; }
    public Node getPredecessor() { return this.predecessor; }
    public void setPredecessor(Node predecessor) { this.predecessor = predecessor; }


}
