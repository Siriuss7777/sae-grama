import java.util.LinkedList;

public class Node {
    private String type; /*"ville", "restaurant", "centre de loisir"*/
    private String name;
    private boolean found = false;
    private LinkedList<Link> links;

    public Node(String type, String name) {
        this.type = type;
        this.name = name;
        this.links = new LinkedList<Link>();
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isFound() { return found; }

    public LinkedList<Link> getLinks() { return links; }

    public void setLinks(LinkedList<Link> links) { this.links = links; }

    public void addLink(String type, int length, Node next){
        Link tmpLink = new Link(type, length, next);
        links.add(tmpLink);
    }
    public void addLink(Link link){
        links.add(link);
    }

    public void removeLink(String type, int length, Node next){
        Link tmpLink = new Link(type, length, next);
        links.remove(tmpLink);
    }
    public void removeLink(Link link){
        links.remove(link);
    }

    public void mark(){
        this.found = true;
    }

    public int getShortestDistance(String name){
        int tempLength = -1;
        for (int i = 0; i<this.links.size(); i++){
            if(name == this.links.get(i).getNext().getName()){
                if(tempLength == -1 || tempLength>this.links.get(i).getLength()){
                    tempLength = this.links.get(i).getLength();
                }
            }
        }
        return tempLength;
    }

}
