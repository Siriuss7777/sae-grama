package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraphSelectionModel;
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

    public mxGraphComponent initializeAffNoeuds(mxMouseAdapter mouseListener) {

        // Colouring vertices differently following their types
        this.colourNodes();
        this.colourLinks();

        // Make the graph zoomable and scrollable with ctrl-mousewheel
        this.makeScrollable();

        // Handle node (vertex) selection
        this.makeNodesSelectableJGX(mouseListener);

        return graphComponent;
    }


    private void initGraph(){
        String linkName = null;

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
        Object[] departementales = new Object[graph.getLinksCount()];
        Object[] nationales = new Object[graph.getLinksCount()];
        Object[] autoroutes = new Object[graph.getLinksCount()];
        Object currentEdge = null;

        for(int i=0; i < this.getEdges().length; i++){

            currentEdge = this.getEdges()[i];
            String type = String.valueOf(graphComponent.getGraph().getModel().getValue(currentEdge));

            if(type.charAt(0) == 'D'){
                departementales[i] = currentEdge;
            }
            else if(type.charAt(0) == 'N'){
                nationales[i] = currentEdge;
            }
            else{
                autoroutes[i] = currentEdge;
            }

            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#3C64B9", departementales);
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#FCA311", nationales);
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#FF3333", autoroutes);

            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1.5", this.getEdges());
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_ENDARROW, "none", this.getEdges());
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FONTCOLOR, "#A9B7C6", this.getEdges());
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", this.getEdges());
        }

    }

    private void makeScrollable(){
        graphComponent.getGraphControl().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
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
            }
        });
    }

    private void makeNodesSelectable(){
        /*


            Deprecated, use makeNodesSelectableJGX instead


         */
        final mxCell[] lastSelectedCell = {(mxCell) graphComponent.getGraph().getModel().getChildAt(graphComponent.getGraph().getDefaultParent(), 0)};   // CHOSE FIRST ELEMENT TO MAKE IT WORK

        graphComponent.getGraphControl().addMouseListener(new mxMouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Check if the mouse is over a cell
                mxCell cell = ((mxCell) graphComponent.getCellAt(e.getX(), e.getY(), false));
                if (cell != null) {
                    //Check if the cell is a vertex
                    if (graphComponent.getGraph().getModel().isVertex(cell)) {
                        String type = String.valueOf(graphComponent.getGraph().getModel().getValue(lastSelectedCell[0]));
                        if (type.charAt(0) == 'R') {
                            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FF0000", new Object[]{lastSelectedCell[0]});
                        }
                        else if (type.charAt(0) == 'L') {
                            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#00FF00", new Object[]{lastSelectedCell[0]});
                        }
                        else if(type.charAt(0) == 'V'){
                            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#0000FF", new Object[]{lastSelectedCell[0]});
                        }
                        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFFF00", new Object[]{cell});
                        lastSelectedCell[0] = cell;
                    }
                }
            }
        });
    }

    private void makeNodesSelectableJGX(mxMouseAdapter mouseListener){
        mxGraphSelectionModel selectionModel = graphComponent.getGraph().getSelectionModel();
        graphComponent.getGraphControl().addMouseListener(mouseListener);
    }

    public void highlightNodes(LinkedList<Node> nodes){
        this.colourNodes();

        int j = 0;

        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "30", this.getVertices());
        mxCell[] table = new mxCell[nodes.size()];
        for(int i = 0; i< nodes.size() ; i++){
            table[i] = findCell((Node) nodes.get(i));
        }
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", table);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFF300", table);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", new Object[]{graphComponent.getGraph().getSelectionCell()});


    }

    public void highlightLinks(LinkedList<Link> links){
        this.colourLinks();

        int j = 0;

        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "30", this.getEdges());
        mxCell[] table = new mxCell[links.size()];
        for(int i = 0; i< links.size(); i++){
            table[i] = findCell((Link) links.get(i));
        }
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKEWIDTH, "2", table);
        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_OPACITY, "100", table);

    }

    public void highlightPath(LinkedList<Node> nodes) {
        this.resetColours();

        this.highlightNodes(nodes);

        LinkedList<Link> links = new LinkedList<Link>();

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
        this.highlightLinks(new LinkedList<Link>(){{add(link);}});
    }
}
