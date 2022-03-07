public class Link {
    private String type;
    private int length;
    private Node next;

    public Link(String type, int length, Node next) {
        this.type = type;
        this.length = length;
        this.next = next;
    }

    public Node getNext(){ return this.next; }

    public String getType() { return type; }

    public int getLength() { return length; }
}
