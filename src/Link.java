public class Link {
    private String type;
    private int length;
    private Node node;

    public Link(String type, int length, Node node) {
        this.type = type;
        this.length = length;
        this.node = node;
    }

    public Node getNode(){ return this.node; }

    public String getType() { return type; }

    public int getLength() { return length; }
}
