package app.main.map;

import app.main.nodes.Link;
import app.main.nodes.LinkType;
import app.main.nodes.Node;
import app.main.nodes.NodeType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class Graph {
    private final LinkedList<Node> nodes;
    private final LinkedList<Link> autorouteLinks;
    private final LinkedList<Link> nationaleLinks;
    private final LinkedList<Link> departementaleLinks;
    private FloydWarshall matrix;

    static int autorouteNumber = 1, nationaleNumber = 1, departementaleNumber = 1;
    static private final int INFINITE = Integer.MAX_VALUE;

    public Graph() {
        this.nodes = new LinkedList<>();
        this.autorouteLinks = new LinkedList<>();
        this.nationaleLinks = new LinkedList<>();
        this.departementaleLinks = new LinkedList<>();
    }

    public void init(String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));

        String line, strType, name;
        String[] links, dividedLink, attributes;

        NodeType nodeType;
        Node currentNode, newNode;

        LinkType linkType;
        Link addedLink, invertedLink;

        int distance;

        // Traitement des nodes ligne par ligne, node créé quand on le rencontre s'il n'existe pas

        while((line = file.readLine()) != null){
            strType = line.substring(0, line.indexOf(','));
            name = line.substring(line.indexOf(',') + 1, line.indexOf(':'));

            nodeType = NodeType.getTypeWithVal(strType);

            assert nodeType != null;
            currentNode = this.createNode(nodeType, name);

            line = line.substring(line.indexOf(':') + 1); // Résultat: A,50::Paris;;
            links = line.split(";"); // Reste de la ligne: A,50::V,Paris

            for(String link: links){
                dividedLink = link.split("::");
                attributes = dividedLink[1].split(",");

                strType = attributes[0];
                nodeType = NodeType.getTypeWithVal(strType);
                name = attributes[1];

                newNode = this.getNodeFromString(strType + "," + name);

                if(newNode == null && nodeType != null){
                    newNode = this.createNode(nodeType, name);
                }

                attributes = dividedLink[0].split(",");

                strType = attributes[0];
                linkType = LinkType.getTypeWithVal(strType);
                distance = Integer.parseInt(attributes[1]);

                addedLink = currentNode.addLink(newNode, linkType, distance);

                invertedLink = newNode.getClosestNeighbour(currentNode);
                if(invertedLink != null){
                    addedLink.setRoadNumber(invertedLink.getRoadNumber());
                } else {
                    if(addedLink.getType() == LinkType.AUTOROUTE){
                        addedLink.setRoadNumber(autorouteNumber);
                        autorouteNumber++;
                        this.autorouteLinks.add(addedLink);
                    }
                    if(addedLink.getType() == LinkType.DEPARTEMENTALE){
                        addedLink.setRoadNumber(departementaleNumber);
                        departementaleNumber++;
                        this.departementaleLinks.add(addedLink);

                    }
                    if(addedLink.getType() == LinkType.NATIONALE){
                        addedLink.setRoadNumber(nationaleNumber);
                        nationaleNumber++;
                        this.nationaleLinks.add(addedLink);
                    }

                }
            }

        }
        file.close();

        this.matrix = new FloydWarshall(this);
        this.matrix.floydWarshall();

    }

    public FloydWarshall getMatrix(){
        return this.matrix;
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

    public Node createNode(NodeType type, String name) {
        Node tempNode = this.getNodeFromString(type + "," + name);

        if (tempNode == null) {
            tempNode = new Node(type, name);
            this.nodes.add(tempNode);
        }
        return tempNode;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public LinkedList<Node> getVilles() {
        LinkedList<Node> tempList = new LinkedList<>();
        for (Node node : this.nodes) {
            if (node.getType() == NodeType.VILLE) {
                tempList.add(node);
            }
        }
        return tempList;
    }

    public String[] getVillesNames() {
        LinkedList<Node> tempList = this.getVilles();
        String[] tempNames = new String[tempList.size()];

        for (int i = 0; i < tempList.size(); i++) {
            tempNames[i] = tempList.get(i).getName();
        }
        return tempNames;
    }

    public int getVillesCount() {
        return this.getVilles().size();
    }

    public LinkedList<Node> getRestaurants() {
        LinkedList<Node> tempList = new LinkedList<>();
        for (Node node : this.nodes) {
            if (node.getType() == NodeType.RESTAURANT) {
                tempList.add(node);
            }
        }
        return tempList;
    }

    public String[] getRestaurantsNames() {
        LinkedList<Node> tempList = this.getRestaurants();
        String[] tempNames = new String[tempList.size()];

        for (int i = 0; i < tempList.size(); i++) {
            tempNames[i] = tempList.get(i).getName();
        }
        return tempNames;
    }

    public int getRestaurantsCount() {
        return this.getRestaurants().size();
    }

    public LinkedList<Node> getLoisirs() {
        LinkedList<Node> tempList = new LinkedList<>();
        for (Node node : this.nodes) {
            if (node.getType() == NodeType.LOISIRS) {
                tempList.add(node);
            }
        }
        return tempList;
    }

    public String[] getLoisirsNames() {
        LinkedList<Node> tempList = this.getLoisirs();
        String[] tempNames = new String[tempList.size()];

        for (int i = 0; i < tempList.size(); i++) {
            tempNames[i] = tempList.get(i).getName();
        }
        return tempNames;
    }

    public int getLoisirsCount() {
        return this.getLoisirs().size();
    }

    public Node getNodeFromString(String string) {
        Node returnedNode = null;
        for (Node node : this.nodes) {
            if (node.toString().equals(string)) {
                returnedNode = node;
            }
        }
        return returnedNode;
    }

    /*----------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS SUR LES LIENS -------------------------------------*/
    /*----------------------------------------------------------------------------------------------------*/

    public void addLink(Link link) {
        if(link.getType() == LinkType.AUTOROUTE){
            this.autorouteLinks.add(link);
        }
        if(link.getType() == LinkType.DEPARTEMENTALE){
            this.departementaleLinks.add(link);
        }
        if(link.getType() == LinkType.NATIONALE){
            this.nationaleLinks.add(link);
        }
    }

    public void linkNodes(Node node1, Node node2, LinkType type, int size) {
        Link link1 = node1.addLink(node2, type, size);
        Link link2 = node2.addLink(node1, type, size);

        this.addLink(link1);
        this.addLink(link2);
    }

    public LinkedList<Link> getAutoroutes() {
        return this.autorouteLinks;
    }

    public int getAutoroutesCount() {
        return this.getAutoroutes().size();
    }

    public String[] getAutoroutesNames() {
        String[] tempNames = new String[autorouteLinks.size()];

        for (int i = 0; i < autorouteLinks.size(); i++) {
            tempNames[i] = autorouteLinks.get(i).toString();
        }
        return tempNames;
    }

    public LinkedList<Link> getNationales() {
        return this.nationaleLinks;
    }

    public int getNationalesCount() {
        return this.getNationales().size();
    }

    public String[] getNationalesNames() {
        String[] tempNames = new String[nationaleLinks.size()];

        for (int i = 0; i < nationaleLinks.size(); i++) {
            tempNames[i] = nationaleLinks.get(i).toString();
        }
        return tempNames;
    }

    public LinkedList<Link> getDepartementales() {
        return this.departementaleLinks;
    }

    public int getDepartementalesCount() {
        return this.getDepartementales().size();
    }

    public String[] getDepartementalesNames() {
        String[] tempNames = new String[departementaleLinks.size()];

        for (int i = 0; i < departementaleLinks.size(); i++) {
            tempNames[i] = departementaleLinks.get(i).toString();
        }
        return tempNames;
    }

    public LinkedList<Link> getLinks() {
        LinkedList<Link> tempList = new LinkedList<>();
        tempList.addAll(this.autorouteLinks);
        tempList.addAll(this.departementaleLinks);
        tempList.addAll(this.nationaleLinks);
        return tempList;
    }

    public int getLinksCount() {
        return this.getLinks().size();
    }

    public Link getLinkFromString(String string){
        Link returnedLink = null;
        for(Link link : this.getLinks()){
            if(link.toString().equals(string)){
                returnedLink = link;
            }
        }
        return returnedLink;
    }


    /*------------------------------------------------------------------------------------------------*/
    /*------------------------------------- OPÉRATIONS GÉNÉRALES -------------------------------------*/
    /*------------------------------------------------------------------------------------------------*/


    public void nDistance(Node fromNode, int distance, int tmpDistance) {
        if (tmpDistance == 0) {
            return;
        }
        for (Node node : fromNode.getNeighboursAsNodes()) {
            if (node.getDistance() > (distance - tmpDistance)) {
                node.setDistance(distance - tmpDistance);
                nDistance(node, distance, tmpDistance - 1);

            }
        }
    }

    public int Distance(Node fromNode, Node toNode, int distance) { // Retourne la distance si la distance entre deux noeuds est inférieur à la distance demandée
        int result = 0;

        if (fromNode == toNode) {
            return result;
        }

        for (Node node : nodes){
            node.setDistance(10000);
        }

        nDistance(fromNode, distance + 1, distance);

        return toNode.getDistance();
    }

    public Node isBetterThan(Node node1, Node node2, String type) { // Retourne le noeud le plus OUVERT / GASTRONOMIQUE / CULTUREL
        int count1stNode = 0;
        int count2ndNode = 0;
        NodeType tmpType = null;
        if (type.equals("OUVERTE")) {
            tmpType = NodeType.VILLE;
        }
        if (type.equals("GASTRONOMIQUE")) {
            tmpType = NodeType.RESTAURANT;
        }
        if (type.equals("CULTURELLE")) {
            tmpType = NodeType.LOISIRS;
        }
        Node nodeOpen = null;
        node1.setDistance(0);
        nDistance(node1, 3, 2);
        for (Node node : this.nodes) {
            if ((node.getDistance() <= 2) && (node.getType().equals(tmpType))) {
                count1stNode++;
            }
        }
        for (Node node : this.nodes) {
            node.setDistance(INFINITE);
        }
        node2.setDistance(0);
        nDistance(node2, 3, 2);
        for (Node node : this.nodes) {
            if ((node.getDistance() <= 2) && (node.getType().equals(tmpType))) {
                count2ndNode++;
            }
        }

        if (count1stNode > count2ndNode) {
            nodeOpen = node1;
        } else {
            nodeOpen = node2;
        }
        return nodeOpen;
    }

    public LinkedList<Node> getPathWith(Node fromNode, Node toNode, Node throughNode) { // Retourne le chemin le plus court entre deux noeuds et passant par un autre

        ArrayList<Node> path;

        path = matrix.getShortestPath(fromNode, throughNode);
        path.remove(path.size() - 1);
        path.addAll(matrix.getShortestPath(throughNode, toNode));

        return new LinkedList<>(path);

    }

    public LinkedList<Node> getPathWith(Node fromNode, Node toNode, Node throughNode1, Node throughNode2) { // Respect the order of the nodes ? (fromNode, throughNode1, throughNode2, toNode)
        ArrayList<Node> path = new ArrayList<>();
        int path1;
        int path2;
        path1 = matrix.lowestDistance(fromNode, throughNode1) + matrix.lowestDistance(throughNode2, toNode);
        path2 = matrix.lowestDistance(fromNode, throughNode2) + matrix.lowestDistance(throughNode1, toNode);

        if (path1 < path2) {
            path = matrix.getShortestPath(fromNode, throughNode1);
            path.remove(path.size() - 1);
            path.addAll(matrix.getShortestPath(throughNode1, throughNode2));
            path.remove(path.size() - 1);
            path.addAll(matrix.getShortestPath(throughNode2, toNode));
        } else {
            path = matrix.getShortestPath(fromNode, throughNode2);
            path.remove(path.size() - 1);
            path.addAll(matrix.getShortestPath(throughNode2, throughNode1));
            path.remove(path.size() - 1);
            path.addAll(matrix.getShortestPath(throughNode1, toNode));
        }
        return new LinkedList<>(path);

    }

    public String toString() {
        StringBuilder returnedString = new StringBuilder("Nodes:");
        for (Node node : this.nodes) {
            returnedString.append("\n\t").append(node.getName());
        }
        returnedString.append("\nLinks:");
//        for (Node node : this.nodes) {
//            for (Link neighbour : node.getAllNeighbours()) {
//                returnedString.append("\n\t")
//                        .append(node.getName())
//                        .append(" -")
//                        .append(neighbour.getType())
//                        .append(",")
//                        .append(neighbour.getDistance())
//                        .append("- ")
//                        .append(neighbour.getNode().getName());
//            }
//        }
        for(Link link : this.getLinks()){
            returnedString.append("\n\t")
                    .append(link.getFromNode().getName())
                    .append(" -")
                    .append(link.getType())
                    .append(link.getRoadNumber())
                    .append(",")
                    .append(link.getDistance())
                    .append("- ")
                    .append(link.getNode().getName());
        }
        return returnedString.toString();
    }
}




