package TestMap;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GraphTest {
    private static Graph graph = new Graph();

    private Node nodeA;
    private Node nodeB;
    private Node nodeC;
    private Node nodeD;
    private Node nodeE;
    private Link linkN1;

    public GraphTest() {
    }

    @Before
    public void setUp() throws IOException {
        String filename = null;
        filename = "src/resources/test.csv";
        graph.init(filename);
        nodeA = graph.getNodeFromString("V,a");
        nodeB = graph.getNodeFromString("V,b");
        nodeC = graph.getNodeFromString("V,c");
        nodeD = graph.getNodeFromString("V,d");
        nodeE = graph.getNodeFromString("V,e");
        linkN1 = graph.getLinkFromString("N1");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGraphInit() {
        assertEquals(5, graph.getNodes().size());
        assertEquals(38, graph.getLinks().size());
    }

    @Test
    public void testVillesCount() {
        assertEquals(5, graph.getVillesCount());
    }

    @Test
    public void testNationalesCount() {
        assertEquals(8, graph.getNationalesCount());
    }

    @Test
    public void testGetNodeFromString() {
        assertEquals("V,a", nodeA.toString());
    }

    @Test
    public void testGetLinkFromString() {
        assertEquals("N1", linkN1.toString());
    }

    @Test
    public void testGetNodeName() {
        assertEquals("a", nodeA.getName());
    }

    @Test
    public void testBetterThan() {
        assertEquals(nodeA, graph.isBetterThan(nodeA, nodeB, "OUVERTE"));
    }

    @Test
    public void testPathWith() {
        ArrayList<Node> list = new ArrayList<>();
        list.add(nodeA);
        list.add(nodeE);
        list.add(nodeB);
        list.add(nodeC);
        assertEquals(list, graph.getPathWith(nodeA, nodeC, nodeB));
    }

    }
