package app.main.nodes;

public class Link {
    private Node node;
    private LinkType type; /* D: "Departementale", N: "Nationale", A: "Autoroute" */
    private int distance;

    public Link(Node node, LinkType type, int distance) {
        this.node = node;
        this.type = type;
        this.distance = distance;
    }

    public Node getNode(){ return this.node; }

    public LinkType getType() { return type; }

    public int getDistance() { return distance; }

    @Override
    public String toString() {
        return node.toString() + " " + type + " " + distance;
    }
}
