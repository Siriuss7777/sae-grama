import java.util.LinkedList;
import java.util.LinkedList;

public class Map {
    private LinkedList<Node> nodes;
    private LinkedList<Link> links;
    private final static int INFINITE = 100000;

    public Map() {
        this.nodes = new LinkedList<Node>();
        this.links = new LinkedList<Link>();
    }

    public Node getNode(int i){
        return this.nodes.get(i);
    }
    public Node getNode(String name){
        for(int i = 0; i<nodes.size(); i++){
            if(this.nodes.get(i).getName() == name){
                return this.nodes.get(i);
            }
        }
        return null;
    }

    /*----------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS SUR LES NŒUDS -------------------------------------*/
    /*----------------------------------------------------------------------------------------------------*/

    public LinkedList<Node> getNodes(){ return this.nodes; }
    public int getNodesCount(){ return this.nodes.size(); }

    public Node createNode(String type, String name){
        Node tempNode = new Node(type, name);
        this.nodes.add(tempNode);
        return tempNode;
    }
    public void addNode(Node node){
        this.nodes.add(node);
    }

    public LinkedList<Node> getVilles(){
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Node node : this.nodes) {
            if (node.getType().equals("V")) {
                tempList.add(node);
            }
        }
        return tempList;
    }
    public int getVillesCount(){
        int count = 0;
        for (Node node : this.nodes) {
            if (node.getType().equals("V")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Node> getRestaurants(){
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Node node : this.nodes) {
            if (node.getType().equals("R")) {
                tempList.add(node);
            }
        }
        return tempList;
    }
    public int getRestaurantsCount(){
        int count = 0;
        for (Node node : this.nodes) {
            if (node.getType().equals("R")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Node> getLoisirs(){
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Node node : this.nodes) {
            if (node.getType().equals("L")) {
                tempList.add(node);
            }
        }
        return tempList;
    }
    public int getLoisirsCount(){
        int count = 0;
        for (Node node : this.nodes) {
            if (node.getType().equals("L")) {
                count++;
            }
        }
        return count;
    }


    /*----------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS SUR LES LIENS -------------------------------------*/
    /*----------------------------------------------------------------------------------------------------*/

    public LinkedList<Link> getLinks() { return links; }
    public int getLinksCount(){ return this.nodes.size(); }

    public Link createLink(Node fromNode, Node toNode, String type, int length){
        Link newLink = new Link(fromNode, toNode, type, length);
        this.links.add(newLink);
        return newLink;
    }
    public void addLink(Link link){
        this.links.add(link);
    }

    public LinkedList<Link> getAutoroutes(){
        LinkedList<Link> tempList = new LinkedList<Link>();
        for (Link link : this.links) {
            if (link.getType().equals("A")) {
                tempList.add(link);
            }
        }
        return tempList;
    }
    public int getAutoroutesCount(){
        int count = 0;
        for (Link link : this.links) {
            if (link.getType().equals("A")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Link> getNationales(){
        LinkedList<Link> tempList = new LinkedList<Link>();
        for (Link link : this.links) {
            if (link.getType().equals("N")) {
                tempList.add(link);
            }
        }
        return tempList;
    }
    public int getNationalesCount(){
        int count = 0;
        for (Link link : this.links) {
            if (link.getType().equals("N")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Link> getDepartementales(){
        LinkedList<Link> tempList = new LinkedList<Link>();
        for (Link link : this.links) {
            if (link.getType().equals("D")) {
                tempList.add(link);
            }
        }
        return tempList;
    }
    public int getDepartementalesCount(){
        int count = 0;
        for (Link link : this.links) {
            if (link.getType().equals("D")) {
                count++;
            }
        }
        return count;
    }

    /*------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS GÉNÉRALES -------------------------------------*/
    /*------------------------------------------------------------------------------------------------*/

    public LinkedList<Node> getNeighbours(Node node){
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Link link : this.links){
            if (link.getFromNode().getName().equals(node.getName())){
                tempList.add(link.getToNode());
            }
        }
        return tempList;
    }
    public boolean isNeighbour (Node fromNode, Node toNode){
        LinkedList<Node> neighboursList = getNeighbours(fromNode);
        return neighboursList.contains(toNode);
    }

    public Link getLinkFromNodes(Node fromNode, Node toNode){
        Link returnedLink = null;
        for(Link link: this.links){
            if(link.getFromNode() == fromNode && link.getToNode() == toNode){
                returnedLink = link;
            }else if(link.getFromNode() == toNode && link.getToNode() == fromNode){
                returnedLink = link;
            }
        }
        return returnedLink;
    }

    public LinkedList<Node> getShortestPath(Node fromNode, Node toNode){
        LinkedList<Node> searchedList = this.nodes;
        LinkedList<Node> returnedList = new LinkedList<>();
        Node shortestDistance = new Node(null, null);
        Node currentNode = fromNode;

        for(Node node: searchedList){
            node.setDistance(INFINITE);
            node.setPredecessor(null);
        }
        fromNode.setDistance(0);

        do {
            searchedList = this.getNeighbours(currentNode);
            shortestDistance = this.getLowestDistance(currentNode);
            for(Node node: this.nodes){
                System.out.println(currentNode.getName());
                System.out.println(node.getName());
                this.updateDistances(currentNode, node);
            }

            for(Node node: searchedList){
                if(node.getDistance()>currentNode.getDistance()){
                    shortestDistance = node;
                }
            }

            searchedList.remove(currentNode);
            returnedList.add(currentNode);
            currentNode = shortestDistance;

        }while(!searchedList.isEmpty());

        return returnedList;
    }

    public Node getLowestDistance(Node node){
        int min = -1, tempMin;
        Node returnedNode = null;
        for(Node forNode: this.getNeighbours(node)){
            tempMin = forNode.getDistance();
            if((tempMin<min)||(min==-1)){
                min = tempMin;
                returnedNode = forNode;
            }
        }
        return returnedNode;
    }

    private void updateDistances(Node node1, Node node2) {
        Link nodesLink = getLinkFromNodes(node1, node2);
        if(nodesLink == null){
            return;
        }
        if (node2.getDistance() > node1.getDistance() + nodesLink.getDistance()) {
            node2.setDistance(node1.getDistance() + nodesLink.getDistance());
            node2.setPredecessor(node1);
        }

    }
}

