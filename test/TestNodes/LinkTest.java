package TestNodes;

import app.main.map.Graph;
import app.main.nodes.LinkType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinkTest {
    static Graph graph = new Graph();

    public LinkTest() {
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
    public void testGetLinkType() {
        assertEquals(LinkType.NATIONAL, graph.getLinkFromString("N1").getType());
    }

    @Test
    public void testGetLinkRoadNumber() {
        assertEquals(1, graph.getLinkFromString("N1").getRoadNumber());
    }

    @Test
    public void testToString() {
        assertEquals("N1", graph.getLinkFromString("N1").toString());
    }

    @Test
    public void testDistance() {
        assertEquals(10, graph.getLinkFromString("N1").getDistance());
    }

    @Test
    public void testNeighbour() {
        assertEquals(graph.getNodeFromString("V,b"),graph.getLinkFromString("N1").getNode());
    }

}
