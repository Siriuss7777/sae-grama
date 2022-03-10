import java.util.LinkedList;

public class Map {
    private LinkedList<Node> nodes;
    private LinkedList<Link> links;
    static private int INFINITE = 100000;

    public Map() {
        this.nodes = new LinkedList<Node>();
        this.links = new LinkedList<Link>();
    }

    /*----------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS SUR LES NŒUDS -------------------------------------*/
    /*----------------------------------------------------------------------------------------------------*/

    public LinkedList<Node> getNodes() {
        return this.nodes;
    }

    public int getNodesCount() {
        return this.nodes.size();
    }

    public Node createNode(String type, String name) {
        Node tempNode = new Node(type, name);
        this.nodes.add(tempNode);
        return tempNode;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public LinkedList<Node> getVilles() {
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Node node : this.nodes) {
            if (node.getType().equals("V")) {
                tempList.add(node);
            }
        }
        return tempList;
    }

    public int getVillesCount() {
        int count = 0;
        for (Node node : this.nodes) {
            if (node.getType().equals("V")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Node> getRestaurants() {
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Node node : this.nodes) {
            if (node.getType().equals("R")) {
                tempList.add(node);
            }
        }
        return tempList;
    }

    public int getRestaurantsCount() {
        int count = 0;
        for (Node node : this.nodes) {
            if (node.getType().equals("R")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Node> getLoisirs() {
        LinkedList<Node> tempList = new LinkedList<Node>();
        for (Node node : this.nodes) {
            if (node.getType().equals("L")) {
                tempList.add(node);
            }
        }
        return tempList;
    }

    public int getLoisirsCount() {
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


    public void linkNodes(Node node1, Node node2, String type, int size) {
        Link link1 = node1.addLink(node2, type, size);
        Link link2 = node2.addLink(node1, type, size);

        this.links.add(link1);
        this.links.add(link2);
    }

    public LinkedList<Link> getAutoroutes() {
        LinkedList<Link> tempList = new LinkedList<Link>();
        for (Link link : this.links) {
            if (link.getType().equals("A")) {
                tempList.add(link);
            }
        }
        return tempList;
    }

    public int getAutoroutesCount() {
        int count = 0;
        for (Link link : this.links) {
            if (link.getType().equals("A")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Link> getNationales() {
        LinkedList<Link> tempList = new LinkedList<Link>();
        for (Link link : this.links) {
            if (link.getType().equals("N")) {
                tempList.add(link);
            }
        }
        return tempList;
    }

    public int getNationalesCount() {
        int count = 0;
        for (Link link : this.links) {
            if (link.getType().equals("N")) {
                count++;
            }
        }
        return count;
    }

    public LinkedList<Link> getDepartementales() {
        LinkedList<Link> tempList = new LinkedList<Link>();
        for (Link link : this.links) {
            if (link.getType().equals("D")) {
                tempList.add(link);
            }
        }
        return tempList;
    }

    public int getDepartementalesCount() {
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

    public String toString() {
        String returnedString = "Nodes:";
        for (Node node : this.nodes) {
            returnedString += "\n\t" + node.getName();
        }
        returnedString += "\nLinks:";
        for (Node node : this.nodes) {
            for (Link neighbour : node.getNeighbours()) {
                returnedString += "\n\t" + node.getName() + " -" + neighbour.getType() + "," + neighbour.getDistance() + "- " + neighbour.getNode().getName();
            }
        }
        return returnedString;
    }

//    public LinkedList<Node> getShortestPath(Node fromNode, Node toNode) {
//        LinkedList<Node> tempList = fromNode.getNeighbourNodes();
//
//        for (Node node : tempList) {
//            node.setDistance(INFINITE);
//        }
//        fromNode.setDistance(0);
//
//        LinkedList<Node> nodesList = this.nodes;
//        LinkedList<Node> shortestPath = new LinkedList<>();
//        Node node1 = new Node(null, null);
//
//        while(!nodesList.isEmpty()){
//            System.out.println(".");
//            node1 = getLowestDistance(fromNode);
//            shortestPath.add(node1);
//            for(Node node: node1.getNeighbourNodes()){
//                updateDistances(node, node1);
//            }
//            nodesList.remove(node1);
//        }
//        while(toNode != fromNode){
//            shortestPath.add(toNode);
//            toNode = toNode.getPredecessor();
//        }
//        shortestPath.add(fromNode);
//
//        return shortestPath;
//    }
//
//    public Node getLowestDistance(Node node) {
//        int min = -1, tempMin;
//        Node returnedNode = null;
//        for (Link link : node.getNeighbours()) {
//            if(!link.getNode().isFound()){
//                tempMin = link.getNode().getDistance();
//                if ((tempMin < min) || (min == -1)) {
//                    min = tempMin;
//                    returnedNode = link.getNode();
//                }
//            }
//        }
//        return returnedNode;
//    }
//
//    private void updateDistances(Node node1, Node node2) {
//        if (node1.getNeighbourNodes().contains(node2) && node2.getNeighbourNodes().contains(node1)){
//            if (node2.getDistance() > node1.getDistance() + node1.getDistance()) {
//                node2.setDistance(node1.getDistance() + node1.getNeighbour(node2).getDistance());
//                node2.setPredecessor(node1);
//            }
//        }
//    }

    public LinkedList<Node> getShortestPath(Node fromNode, Node toNode){
        LinkedList<Node> searchedList = new LinkedList<>();
        LinkedList<Node> shortestPath = new LinkedList<>();
        Node currentNode = new Node(null, null);
        for(Node node: this.nodes){
            node.setDistance(INFINITE);
            node.setPredecessor(currentNode);
        }
        fromNode.setDistance(0);

        searchedList.add(fromNode);
        while (!searchedList.isEmpty()){
            System.out.println(".");
            currentNode = searchedList.element();
            searchedList.removeLast();

            if(currentNode == toNode){
                break;
            }
            currentNode.setFound(true);
            for(Node node: currentNode.getNeighbourNodes()){
                if(!node.isFound()){
                    node.setDistance(currentNode.getDistance() + this.getLink(currentNode, node).getDistance());
                    searchedList.add(node);
                }
            }
        }

        return shortestPath;
    }

    public Link getLink(Node node1, Node node2) {
        Link returnedLink = null;
        if (node1.getNeighbourNodes().contains(node2) && node2.getNeighbourNodes().contains(node1)) {
            returnedLink = node1.getNeighbour(node2);
        }
        return returnedLink;
    }
}

