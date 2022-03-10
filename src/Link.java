public class Link {
    private Node node;
    private String type;
    private int length;

    public Link(Node node, String type, int length) {
        this.node = node;
        this.type = type;
        this.length = length;
    }

    public Node getNode(){ return this.node; }

    public String getType() { return type; }

    public int getLength() { return length; }

}
