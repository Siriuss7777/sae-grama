package app.main.map;

import app.main.nodes.Node;

/**
 * @author Matéo Guenot, Bastien Le Gall
 */

public class DistancePred {
    private int distance;
    private Node pred;
    private final int INFINITE = 100000;

    public DistancePred() {
        this.distance = INFINITE;
        this.pred = null;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getPredecessor() {
        return pred;
    }

    public void setPredecessor(Node pred) {
        this.pred = pred;
    }

    public void update(int distance, Node pred) {
        this.distance = distance;
        this.pred = pred;
    }

    public String toString() {
//        return new StringBuffer().append(distance).append(pred).toString();
        return "{" + distance + " " + pred + "}";
    }
}
