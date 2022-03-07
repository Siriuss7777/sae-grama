import java.util.LinkedList;

public class Map {
    private LinkedList<Node> nodes;

    public Map() {
        this.nodes = new LinkedList<Node>();
    }

    public void add(Node node){
        this.nodes.add(node);
    }

    public Node get(int i){
        return this.nodes.get(i);
    }
    public Node get(String name){
        for(int i = 0; i<nodes.size(); i++){
            if(this.nodes.get(i).getName() == name){
                return this.nodes.get(i);
            }
        }
        return null;
    }

    public int getVilles(){

    }
}
