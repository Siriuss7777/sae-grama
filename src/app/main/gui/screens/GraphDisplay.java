package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;
import com.mxgraph.util.mxConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class GraphDisplay extends JPanel {

    private static Graph graph;
    private static mxGraphComponent graphComponent;



    public static final mxMouseAdapter DEFAULT_MOUSELISTENER = new mxMouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            mxCell cell = ((mxCell) graphComponent.getCellAt(e.getX(), e.getY(), false));
            if (cell != null) {
                graphComponent.getGraph().setSelectionCell(cell);
            }
            else{
                graphComponent.getGraph().clearSelection();
            }
        }
    };

    public GraphDisplay(Graph g) {
        super();
        graph = g;
        this.initGraph();
    }

    public mxGraphComponent initNodeDisplay(mxMouseAdapter mouseListener) {

        // Colouring vertices differently following their types
        this.colourNodes();
        this.colourLinks();

        // Make the graph zoomable and scrollable with ctrl-mousewheel
        this.enableScrolling();

        // Handle node (vertex) selection
        this.enableSelection(mouseListener);

        return graphComponent;
    }


    private void initGraph(){
        String linkName;

        ListenableGraph<Node, String> g = new DefaultListenableGraph<>(new DefaultUndirectedGraph<>(String.class));

        for (Node node : graph.getNodes()) {
            g.addVertex(node);
        }

        for (Link road : graph.getAutoroutes()) {
            linkName = road.getType().toString() + road.getRoadNumber() + " (" + road.getDistance() + "km)";
            g.addEdge(road.getFromNode(), road.getNode(), linkName);
        }

        for (Link road : graph.getNationales()) {
            linkName = road.getType().toString() + road.getRoadNumber() + " (" + road.getDistance() + "km)";
            g.addEdge(road.getFromNode(), road.getNode(), linkName);
        }

        for (Link road : graph.getDepartementales()) {
            linkName = road.getType().toString() + road.getRoadNumber() + " (" + road.getDistance() + "km)";
            g.addEdge(road.getFromNode(), road.getNode(), linkName);
        }



        JGraphXAdapter<Node, String> jgxa = new JGraphXAdapter<>(g);
        graphComponent = new mxGraphComponent(jgxa);
        graphComponent.setConnectable(false);
        graphComponent.setEnabled(false);
        graphComponent.setToolTips(true);
        graphComponent.setCenterZoom(false);
        graphComponent.setCenterPage(false);
        jgxa.setCellsSelectable(true);


        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxa);
        layout.setForceConstant(250);
        layout.execute(jgxa.getDefaultParent());

    }

    public void colourNodes() {
        Object[] restaurants = new Object[graph.getNodes().size()];
        Object[] villes = new Object[graph.getNodes().size()];
        Object[] loisirs = new Object[graph.getNodes().size()];
        Object currentVertex;
        for (int i = 0; i < graph.getNodesCount(); i++) {

            currentVertex = this.getCells()[i];
            String type = String.valueOf(graphComponent.getGraph().getModel().getValue(currentVertex));

            if (type.charAt(0) == 'R') {
                restaurants[i] = currentVertex;
            } else if (type.charAt(0) == 'L') {
                loisirs[i] = currentVertex;
            } else {
                villes[i] = currentVertex;
            }
        }
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#3C64B9", restaurants);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FCA311", loisirs);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FF3333", villes);

        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FONTCOLOR, "#A9B7C6", this.getCells());
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", this.getCells());

    }

    public void colourLinks(){
        Object[] departmentalRoads = new Object[graph.getLinksCount()];
        Object[] nationalRoads = new Object[graph.getLinksCount()];
        Object[] highways = new Object[graph.getLinksCount()];
        Object currentEdge;

        for(int i=0; i < this.getEdges().length; i++){

            currentEdge = this.getEdges()[i];
            String type = String.valueOf(graphComponent.getGraph().getModel().getValue(currentEdge));

            if(type.charAt(0) == 'D'){
                departmentalRoads[i] = currentEdge;
            }
            else if(type.charAt(0) == 'N'){
                nationalRoads[i] = currentEdge;
            }
            else{
                highways[i] = currentEdge;
            }

            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#3C64B9", departmentalRoads);
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#FCA311", nationalRoads);
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#FF3333", highways);

            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1.5", this.getEdges());
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_ENDARROW, "none", this.getEdges());
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FONTCOLOR, "#A9B7C6", this.getEdges());
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", this.getEdges());
        }

    }

    private void enableScrolling(){
        graphComponent.getGraphControl().addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                if (e.getWheelRotation() < 0) {
                    graphComponent.zoomIn();
                } else {
                    graphComponent.zoomOut();
                }
            }
            // Normal scroll if control is not pressed
            else if (e.isShiftDown()) {
                if (!e.isControlDown()) {
                    graphComponent.getViewport().setViewPosition(new Point(
                            graphComponent.getViewport().getViewPosition().x + e.getWheelRotation() * 15,
                            graphComponent.getViewport().getViewPosition().y));
                } else {
                    graphComponent.getViewport().setViewPosition(new Point(
                            graphComponent.getViewport().getViewPosition().x - e.getWheelRotation() * 15,
                            graphComponent.getViewport().getViewPosition().y));
                }
            } else {
                if (!e.isControlDown()) {
                    graphComponent.getViewport().setViewPosition(new Point(
                            graphComponent.getViewport().getViewPosition().x,
                            graphComponent.getViewport().getViewPosition().y + e.getWheelRotation() * 15));
                } else {
                    graphComponent.getViewport().setViewPosition(new Point(
                            graphComponent.getViewport().getViewPosition().x,
                            graphComponent.getViewport().getViewPosition().y - e.getWheelRotation() * 15));
                }
            }
        });
    }


    private void enableSelection(mxMouseAdapter mouseListener){
        graphComponent.getGraphControl().addMouseListener(mouseListener);
    }

    public void highlightNodes(LinkedList<Node> nodes){
        this.colourNodes();

        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "30", this.getVertices());
        mxCell[] table = new mxCell[nodes.size()];
        for(int i = 0; i< nodes.size() ; i++){
            table[i] = findCell(nodes.get(i));
        }
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", table);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFF300", table);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", new Object[]{graphComponent.getGraph().getSelectionCell()});


    }

    public void highlightLinks(LinkedList<Link> links){
        this.colourLinks();


        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "30", this.getEdges());
        mxCell[] table = new mxCell[links.size()];
        for(int i = 0; i< links.size(); i++){
            table[i] = findCell(links.get(i));
        }
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKEWIDTH, "2", table);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", table);

    }

    public void highlightPath(LinkedList<Node> nodes) {
        this.resetColours();

        this.highlightNodes(nodes);

        LinkedList<Link> links = new LinkedList<>();

        for(int i = 0; i < nodes.size() - 1; i++) {
            links.add(nodes.get(i).getClosestNeighbour(nodes.get(i+1)));
        }

        this.highlightLinks(links);

    }

    public mxCell findCell(Node node){
        mxCell returnedCell = null;
        for(Object cell: this.getCells()){
            if(graphComponent.getGraph().getModel().getValue(cell).equals(node)){
                returnedCell = (mxCell) cell;
                break;
            }
        }
        return returnedCell;
    }

    public mxCell findCell(Link link){
        mxCell returnedCell = null;
        String linkValue = link.getType().toString() + link.getRoadNumber() + " (" + link.getDistance() + "km)";
        for(Object cell: this.getCells()){
            if(graphComponent.getGraph().getModel().getValue(cell).equals(linkValue)){
                returnedCell = (mxCell) cell;
                break;
            }
        }
        return returnedCell;
    }

    public Link findLink(mxCell cell){
        Link returnedLink = null;
        String linkValue = graphComponent.getGraph().getModel().getValue(cell).toString();
        for(Link link: graph.getLinks()){
            if((link.getType().toString() + link.getRoadNumber() + " (" + link.getDistance() + "km)").equals(linkValue)){
                returnedLink = link;
                break;
            }
        }
        return returnedLink;
    }

    public Object[] getCells(){
        return graphComponent.getGraph().getChildCells(graphComponent.getGraph().getDefaultParent());
    }

    public Object[] getVertices(){
        return graphComponent.getGraph().getChildVertices(graphComponent.getGraph().getDefaultParent());
    }

    public Object[] getEdges(){
        return graphComponent.getGraph().getChildEdges(graphComponent.getGraph().getDefaultParent());
    }

    public void resetColours(){
        this.colourNodes();
        this.colourLinks();
    }


    public void highlightNodesByLink(Link link) {
        LinkedList<Node> vertices = new LinkedList<>();
        vertices.add(link.getNode());
        vertices.add(link.getFromNode());
        this.highlightNodes(vertices);
        this.highlightLinks(new LinkedList<>(){{add(link);}});
    }
}
