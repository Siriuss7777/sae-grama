package TestMap;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FloydWarshallTest {
    private static Graph graph = new Graph();

    private Node nodeA;
    private Node nodeB;
    private Node nodeC;
    private Node nodeE;

    public FloydWarshallTest() {
    }

    @Before
    public void setUp() throws IOException {
        String filename = null;
        filename = "src/resources/test.csv";
        graph.init(filename);
        nodeA = graph.getNodeFromString("V,a");
        nodeB = graph.getNodeFromString("V,b");
        nodeC = graph.getNodeFromString("V,c");
        nodeE = graph.getNodeFromString("V,e");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testShortestPath() {
        ArrayList<Node> list = new ArrayList<>();
        list.add(nodeA);
        list.add(nodeE);
        list.add(nodeB);
        list.add(nodeC);

        assertEquals(list, graph.getMatrix().getShortestPath(graph.getNodeFromString("V,a"), graph.getNodeFromString("V,c")));
    }

    @Test
    public void testDistance(){
        assertEquals(9, graph.getMatrix().lowestDistance(nodeA, nodeC));
    }
}
