package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.LinkType;
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


        for (Node node : graph.getNodes()) {
            for (Link neighbour : node.getAllNeighbours()) {
                if(neighbour.getType() == LinkType.AUTOROUTE){
                    linkName = "A" + neighbour.getRoadNumber();
                }
                if(neighbour.getType() == LinkType.NATIONALE){
                    linkName = "N" + neighbour.getRoadNumber();
                }
                if(neighbour.getType() == LinkType.DEPARTEMENTALE){
                    linkName = "D" + neighbour.getRoadNumber();
                }

                g.addEdge(node, neighbour.getNode(), linkName + " (" + neighbour.getDistance()+"km)");
            }
        }


        JGraphXAdapter<Node, String> jgxa = new JGraphXAdapter<>(g);
        graphComponent = new mxGraphComponent(jgxa);
        graphComponent.setConnectable(false);
        graphComponent.setEnabled(false);
        graphComponent.setToolTips(true);
        jgxa.setCellsSelectable(true);


        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxa);
        layout.setForceConstant(250);
        layout.execute(jgxa.getDefaultParent());

    }

    private void colourNodes() {
        for (Object vertex : graphComponent.getGraph().getChildVertices(graphComponent.getGraph().getDefaultParent())) {
            String type = String.valueOf(graphComponent.getGraph().getModel().getValue(vertex));
            if (type.charAt(0) == 'R') {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#3C64B9", new Object[]{vertex});
            } else if (type.charAt(0) == 'L') {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FCA311", new Object[]{vertex});
            } else {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FF3333", new Object[]{vertex});
            }
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FONTCOLOR, "#A9B7C6", new Object[]{vertex});

        }
    }

    private void colourLinks(){
        for(Object edge: graphComponent.getGraph().getChildEdges(graphComponent.getGraph().getDefaultParent())){
            String type = String.valueOf(graphComponent.getGraph().getModel().getValue(edge));
            if (type.charAt(0) == 'D') {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#3C64B9", new Object[]{edge});
            }
            else if (type.charAt(0) == 'N') {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#FCA311", new Object[]{edge});
            }
            else{
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKECOLOR, "#FF3333", new Object[]{edge});
            }
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1.5", new Object[]{edge});
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_ENDARROW, "none", new Object[]{edge});
            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FONTCOLOR, "#A9B7C6", new Object[]{edge});
        }

    }

    // Make the graph zoomable and scrollable with ctrl-mousewheel
    private void makeScrollable2() {
        graphComponent.setAutoScroll(true);
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
        graphComponent.getGraphControl().addMouseListener(mouseListener);
    }
}
