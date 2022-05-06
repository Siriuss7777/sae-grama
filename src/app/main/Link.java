package app.main;

public class Link {
    private Node node;
    private String type; /* D: "Departementale", N: "Nationale", A: "Autoroute" */
    private int distance;

    public Link(Node node, String type, int distance) {
        this.node = node;
        this.type = type;
        this.distance = distance;
    }

    public Node getNode(){ return this.node; }

    public String getType() { return type; }

    public int getDistance() { return distance; }

    @Override
    public String toString() {
        return "app.app.main.launcher.main.Link{" +
                "node=" + node +
                ", type='" + type + '\'' +
                ", distance=" + distance +
                '}';
    }
}
