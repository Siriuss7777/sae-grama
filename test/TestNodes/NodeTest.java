package TestNodes;

import app.main.map.Graph;
import app.main.nodes.NodeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    static Graph graph = new Graph();

    public NodeTest() {
    }

    @Before
    public void setUp() throws IOException {
        String filename = null;
        filename = "src/resources/test.csv";
        graph.init(filename);
    }


    @After
    public void tearDown() {
    }

    @Test
    public void testGetNodeType() {
        assertEquals(NodeType.VILLE, graph.getNodeFromString("V,a").getType());
    }

    @Test
    public void testGetNodeName() {
        assertEquals("a", graph.getNodeFromString("V,a").getName());
    }

    @Test
    public void testGetNodeId() {
        assertEquals(0, graph.getNodeFromString("V,a").getId());
    }

    @Test
    public void testNodeToString() {
        assertEquals("V,a", graph.getNodeFromString("V,a").toString());
    }

    @Test
    public void testIsNeighbour() {
        assertTrue(graph.getNodeFromString("V,a").isNeighbour(graph.getNodeFromString("V,b")));
    }

    @Test
    public void testFarthestNode() {
        assertEquals(graph.getLinkFromString("N1"), graph.getNodeFromString("V,a").getFarthestNeighbour(graph.getNodeFromString("V,b")));
    }

    @Test
    public void testClosestNeighbour() {
        assertEquals(graph.getLinkFromString("N1"), graph.getNodeFromString("V,a").getClosestNeighbour(graph.getNodeFromString("V,b")));
    }

}
