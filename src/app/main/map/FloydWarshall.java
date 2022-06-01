package app.main.map;

import app.main.nodes.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class FloydWarshall {

    private final static int INFINITE = Integer.MAX_VALUE;
    private Map map;
    private DistancePred[][] matrix;

    public FloydWarshall(Map map) {
        this.map = map;
    }

    private void initializeMatrix(DistancePred[][] matrix) {
        LinkedList<Node> nodes = map.getNodes();
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

    public void floydWarshall(){
        DistancePred[][] matrix = new DistancePred[map.getNodesCount()][map.getNodesCount()];

        this.initializeMatrix(matrix);

        for (int k = 0; k < matrix.length; k++) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j].getDistance() > matrix[i][k].getDistance() + matrix[k][j].getDistance()) {
                        matrix[i][j].setDistance(matrix[i][k].getDistance() + matrix[k][j].getDistance());
                        matrix[i][j].setPredecessor(matrix[k][j].getPredecessor());
                    }

                }
            }
        }

        this.matrix = matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.matrix.length; i++) {
            for(int j = 0; j < this.matrix.length; j++){
                sb.append(this.matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getLine(int index){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.matrix.length; i++){
            sb.append(this.matrix[index][i]).append(" ");
        }
        return sb.toString();
    }

    public ArrayList<Node> getShortestPath(Node fromNode, Node toNode){ // Retourne la liste des noeuds constituant le plus court chemin entre deux noeuds
        int nodeId = fromNode.getId();
        int targetId = toNode.getId();
        ArrayList<Node> path = new ArrayList<>();
        path.add(toNode);

        while(nodeId != targetId){ // Tant que l'on n'est pas arrivé au noeud de destination on ajoute.
            path.add(this.matrix[nodeId][targetId].getPredecessor());
            targetId = this.matrix[nodeId][targetId].getPredecessor().getId();
        }

        Collections.reverse(path); // Mettre dans le bonne ordre

        return path;
    }

    public int lowestDistance(Node fromNode, Node toNode){
        int nodeId = fromNode.getId();
        int targetId = toNode.getId();
        return this.matrix[nodeId][targetId].getDistance();
    }


}
