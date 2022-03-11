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


    public LinkedList<Node> getShortestPath(Node fromNode, Node toNode) {
        LinkedList<Node> untreatedNodes = new LinkedList<>();
        LinkedList<Node> treatedNodes = new LinkedList<>();
        LinkedList<Node> shortestPath = null;
        int length;

        for (Node node : this.nodes) {
            node.setDistance(INFINITE);
        }
        fromNode.setDistance(0);
        untreatedNodes.add(fromNode);

        while(untreatedNodes.size()!=0){
            Node currentNode = getLowestDistance(untreatedNodes);
            untreatedNodes.remove(currentNode);
            for(Node neighbourNode : currentNode.getNeighbourNodes()){
                length = neighbourNode.getClosestNeighbour(currentNode).getDistance();
                if(!treatedNodes.contains(neighbourNode)){
                    updateDistances(neighbourNode, length, currentNode);
                    untreatedNodes.add(neighbourNode);
                }
            }
            treatedNodes.add(currentNode);
        }
        toNode.getShortestPath().add(toNode);
        return toNode.getShortestPath();
    }

    public Node getLowestDistance(LinkedList<Node> nodesList) {
        int min = INFINITE;
        int nodeDistance;
        Node returnedNode = null;
        for (Node node : nodesList) {
            nodeDistance = node.getDistance();
            if(nodeDistance < min){
                min = nodeDistance;
                returnedNode = node;
            }
        }
        return returnedNode;
    }

    private void updateDistances(Node node1, int distance, Node node2) {
        int currentNodeDistance = node2.getDistance();
        if(currentNodeDistance + distance < node1.getDistance()){
            node1.setDistance(currentNodeDistance + distance);
            LinkedList<Node> shortestPath = new LinkedList<>(node2.getShortestPath());
            shortestPath.add(node2);
            node1.setShortestPath(shortestPath);
        }

    }

    public int getShortestDistance(Node fromNode, Node toNode){
        LinkedList<Node> shortestPath = this.getShortestPath(fromNode, toNode);
        return toNode.getDistance();
    }
}


