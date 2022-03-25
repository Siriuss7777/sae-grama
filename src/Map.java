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
        Node tempNode;
        if((tempNode = this.getNodeFromString(type+","+name)) == null){
            tempNode = new Node(type, name);
            this.nodes.add(tempNode);
        }
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

    public Node getNodeFromString(String string){
        Node returnedNode = null;
        for(Node node: this.nodes){
            if(node.asString().equals(string)){
                returnedNode = node;
            }
        }
        return returnedNode;
    }

    /*----------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS SUR LES LIENS -------------------------------------*/
    /*----------------------------------------------------------------------------------------------------*/

    public void addLink(Link link){
        this.links.add(link);
    }

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

    //Dijkstra algorithm to find the shortest path between two nodes

    public LinkedList<Node> getShortestPath(Node fromNode, Node toNode) {
        LinkedList<Node> untreatedNodes = new LinkedList<>();
        LinkedList<Node> treatedNodes = new LinkedList<>();
        toNode.setShortestPath(null);
        int length;

        for (Node node : this.nodes) {
            node.setDistance(INFINITE);
            node.setShortestPath(new LinkedList<>());
        }
        fromNode.setDistance(0);
        untreatedNodes.add(fromNode);

        while(!untreatedNodes.isEmpty()){
            Node currentNode = getClosestNode(untreatedNodes);
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

    public Node getClosestNode(LinkedList<Node> neighboursList) {
        int min = INFINITE;
        int nodeDistance;
        Node returnedNode = null;
        for (Node node : neighboursList) {
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

    public void nDistance(Node fromNode, int distance, int tmpDistance){
        boolean result = false;
        if (tmpDistance == 0){
            return;
        }
        for (Node node : fromNode.getNeighbourNodes()){
            if (node.getDistance() > (distance - tmpDistance)){
                node.setDistance(distance - tmpDistance);
                nDistance(node,distance,tmpDistance-1);

            }
        }
    }

    public boolean Distance(Node fromNode, Node toNode, int distance){
        boolean result = false;
        nDistance(fromNode,distance + 1,distance);
        if (toNode.getDistance() <= distance){
            result = true;
        }
        return result;
    }

    public Node isBetterThan(Node node1, Node node2, String type){
        int count1 = 0;
        int count2 = 0;
        String tmpType = null;
        if (type.equals("OUVERTE")){
            tmpType = "V";
        }
        if (type.equals("GASTRONOMIQUE")){
            tmpType = "R";
        }
        if (type.equals("CULTURELLE")){
            tmpType = "L";
        }
        Node nodeOpen = null;
        node1.setDistance(0);
        nDistance(node1, 3, 2);
        for (Node node : this.nodes){
            if ((node.getDistance() <= 2) && (node.getType().equals(tmpType))){
                count1++;
            }
        }
        for (Node node : this.nodes){
            node.setDistance(INFINITE);
        }
        node2.setDistance(0);
        nDistance(node2, 3, 2);
        for (Node node : this.nodes){
            if ((node.getDistance() <= 2) && (node.getType().equals(tmpType))){
                count2++;
            }
        }

        if (count1 > count2){
            nodeOpen = node1;
        }else {
            nodeOpen = node2;
        }
        return nodeOpen;
    }

    public LinkedList<Node> getPathWith(Node fromNode, Node throughNode, Node toNode){
        LinkedList<Node> firstPath = new LinkedList<>();
        LinkedList<Node> lastPath = new LinkedList<>();

        firstPath = getShortestPath(fromNode, throughNode);
        firstPath.removeLast();
        lastPath = getShortestPath(throughNode, toNode);

        firstPath.addAll(lastPath);
        return firstPath;
    }

    //Find a path between two nodes through two given nodes
    public LinkedList<Node> getPathWith(Node fromNode, Node toNode, Node throughNode1, Node throughNode2){
        LinkedList<Node> finalPath = new LinkedList<>();
        LinkedList<Node> finalPath2 = new LinkedList<>();
        LinkedList<Node> tempPath = new LinkedList<>();
        LinkedList<Node> lastPath = new LinkedList<>();
        int tmpDistance = 0;

        finalPath = getShortestPath(fromNode, throughNode1);
        finalPath.removeLast();
        tempPath = getShortestPath(throughNode1, throughNode2);
        tempPath.removeLast();
        lastPath = getShortestPath(throughNode2, toNode);
        finalPath.addAll(tempPath);
        finalPath.addAll(lastPath);
        tmpDistance = throughNode1.getDistance() + throughNode2.getDistance() + toNode.getDistance();

        finalPath2 = getShortestPath(fromNode, throughNode2);
        finalPath2.removeLast();
        tempPath = getShortestPath(throughNode2, throughNode1);
        tempPath.removeLast();
        lastPath = getShortestPath(throughNode1, toNode);
        finalPath2.addAll(tempPath);
        finalPath2.addAll(lastPath);


        if(throughNode1.getDistance() + throughNode2.getDistance() + toNode.getDistance() < tmpDistance){
            finalPath = finalPath2;
        }

        return finalPath;
    }


}


