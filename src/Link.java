public class Link {
    private Node fromNode;
    private Node toNode;
    private String type; /*"autoroute", "nationale", "départementale"*/
    private int distance;

    public Link(Node fromNode, Node toNode, String type, int distance) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.type = type;
        this.distance = distance;
    }

    public Node getFromNode(){ return this.fromNode; }

    public Node getToNode(){ return this.toNode; }

    public String getType() { return type; }

    public int getDistance() { return distance; }
}
