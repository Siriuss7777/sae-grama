package app.main.map;

import app.main.nodes.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Matéo Guenot, Bastien Le Gall
 */

public class FloydWarshall {

    private final static int INFINITE = Integer.MAX_VALUE;
    private final Graph graph;
    private DistancePred[][] matrix;

    public FloydWarshall(Graph map) {
        this.graph = map;
    }

    /**
     * @param matrix : DistancePred matrix
     *               Initialize the matrix with the distances between each nodes
     */
    private void initializeMatrix(DistancePred[][] matrix) {
        LinkedList<Node> nodes = graph.getNodes();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new DistancePred();
                if (i == j) {
                    matrix[i][j].setDistance(0);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (nodes.get(i).isNeighbour(nodes.get(j))) {
                    if (nodes.get(i).getClosestNeighbour(nodes.get(j)).getDistance() < matrix[i][j].getDistance()) {
                        matrix[i][j].setDistance(nodes.get(i).getClosestNeighbour(nodes.get(j)).getDistance());
                        matrix[i][j].setPredecessor(nodes.get(i));
                    }
                }
            }
        }

    }

    public void floydWarshall() {
        DistancePred[][] matrix = new DistancePred[graph.getNodesCount()][graph.getNodesCount()];

        this.initializeMatrix(matrix);

        for (int k = 0; k < matrix.length; k++) {
            for (DistancePred[] distancePreds : matrix) {
                for (int j = 0; j < matrix.length; j++) {
                    if (distancePreds[j].getDistance() > distancePreds[k].getDistance() + matrix[k][j].getDistance()) {
                        distancePreds[j].setDistance(distancePreds[k].getDistance() + matrix[k][j].getDistance());
                        distancePreds[j].setPredecessor(matrix[k][j].getPredecessor());
                    }

                }
            }
        }

        this.matrix = matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DistancePred[] distancePreds : this.matrix) {
            for (int j = 0; j < this.matrix.length; j++) {
                sb.append(distancePreds[j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getLine(int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.matrix.length; i++) {
            sb.append(this.matrix[index][i]).append(" ");
        }
        return sb.toString();
    }

    /**
     * @param fromNode : Node from which we want to know the shortest path to another node
     * @param toNode  : Node to which we want to know the shortest path
     * @return : Lsit of nodes that represent the shortest path
     */
    public ArrayList<Node> getShortestPath(Node fromNode, Node toNode) {
        int nodeId = fromNode.getId();
        int targetId = toNode.getId();
        ArrayList<Node> path = new ArrayList<>();
        path.add(toNode);

        while (nodeId != targetId) { // Tant que l'on n'est pas arrivé au noeud de destination on ajoute.
            path.add(this.matrix[nodeId][targetId].getPredecessor());
            targetId = this.matrix[nodeId][targetId].getPredecessor().getId();
        }

        Collections.reverse(path); // Mettre dans le bonne ordre

        return path;
    }

    /**
     * @param fromNode : Node from which we want to know the shortest path to another node
     * @param toNode : Node to which we want to know the shortest path
     * @return : The lowest distance between the two nodes
     */
    public int lowestDistance(Node fromNode, Node toNode) {
        int nodeId = fromNode.getId();
        int targetId = toNode.getId();
        return this.matrix[nodeId][targetId].getDistance();
    }


}
