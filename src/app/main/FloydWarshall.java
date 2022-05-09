package app.main;

import java.util.LinkedList;

public class FloydWarshall{

    private final static int INFINITE = Integer.MAX_VALUE;

    public FloydWarshall() {
    }

    public static DistancePred[][] floydWarshall(Map map){
        DistancePred[][] matrix = new DistancePred[map.getNodesCount()][map.getNodesCount()];
        LinkedList<Node> nodes = map.getNodes();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new DistancePred();
                if (i == j){
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
        // Initialisation marche pour sure

        for (int z = 0; z < matrix.length; z++) { // 4 boucles ça fais beaucoup la non ???
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[i][k].getDistance() != INFINITE && matrix[k][j].getDistance() != INFINITE) {
                            if (matrix[i][j].getDistance() > matrix[i][k].getDistance() + matrix[k][j].getDistance()) {
                                matrix[i][j].setDistance(matrix[i][k].getDistance() + matrix[k][j].getDistance());
                                matrix[i][j].setPredecessor(matrix[k][j].getPredecessor()); // Cette ligne est fonctionnelle
                            }
                        }
                    }
                }
            }
        }

        return matrix;
    }


}
