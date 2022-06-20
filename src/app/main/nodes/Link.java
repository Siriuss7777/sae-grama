package app.main.nodes;

public class Link {
    private final Node node;
    private final LinkType type; /* D: "Departementale", N: "Nationale", A: "Autoroute" */
    private final int distance;

    private int roadNumber;
    private Node fromNode;

    public Link(Node node, LinkType type, int distance) {
        this.node = node;
        this.type = type;
        this.distance = distance;
    }

    public Link(Node node, LinkType type, int distance, Node fromNode) {
        this.node = node;
        this.type = type;
        this.distance = distance;
        this.fromNode = fromNode;
    }

    public Node getNode() {
        return this.node;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public void setFromNode(Node fromNode) {
        this.fromNode = fromNode;
    }

    public int getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(int roadNumber) {
        this.roadNumber = roadNumber;
    }

    public LinkType getType() {
        return type;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return type.toString() + roadNumber;
    }
}
