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
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphDisplay extends JPanel {

    private static Graph graph;
    private static mxGraphComponent graphComponent;
    private static ListenableGraph<Node, DefaultEdge> listenableGraph;
    private static JGraphXAdapter<Node, DefaultEdge> jgxAdapter;


    public static final mxMouseAdapter DEFAULT_MOUSELISTENER = new mxMouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            mxCell cell = ((mxCell) graphComponent.getCellAt(e.getX(), e.getY(), false));
            if (cell != null) {
                graphComponent.getGraph().setSelectionCell(cell);
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

        // Make the graph zoomable and scrollable with ctrl-mousewheel
        this.makeScrollable();

        // Handle node (vertex) selection
//        this.makeNodesSelectable();
        this.makeNodesSelectableJGX(mouseListener);

        return graphComponent;
    }

    private void initGraph(){
        ListenableGraph<Node, DefaultEdge> g = new DefaultListenableGraph<>(new DirectedWeightedPseudograph<>(DefaultEdge.class));

        for (Node node : graph.getNodes()) {
            g.addVertex(node);
        }

        for (Node node : graph.getNodes()) {
            for (Link neighbour : node.getAllNeighbours()) {
                g.setEdgeWeight(g.addEdge(node,
                        neighbour.getNode()), neighbour.getDistance());
            }
        }

        JGraphXAdapter<Node, DefaultEdge> jgxa = new JGraphXAdapter<>(g);
        graphComponent = new mxGraphComponent(jgxa);
        graphComponent.setConnectable(false);
        graphComponent.setEnabled(false);
        jgxa.setCellsSelectable(true);


        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxa);
        layout.setForceConstant(200);
        layout.execute(jgxa.getDefaultParent());


        listenableGraph = g;
        jgxAdapter = jgxa;
    }

    private void colourNodes() {
        for (Object vertex : graphComponent.getGraph().getChildVertices(graphComponent.getGraph().getDefaultParent())) {
            String type = String.valueOf(graphComponent.getGraph().getModel().getValue(vertex));
            if (type.charAt(0) == 'R') {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FF0000", new Object[]{vertex});
            }
            else if (type.charAt(0) == 'L') {
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#00FF00", new Object[]{vertex});
            }
            else{
                graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#0000FF", new Object[]{vertex});
            }
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
            Outdated, use makeNodesSelectableJGX instead
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
                        System.out.println(type);
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
