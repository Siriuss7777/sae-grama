package app.main.gui.screens;

import app.main.map.Graph;
import app.main.nodes.Link;
import app.main.nodes.Node;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

public class GraphDisplay extends JPanel {

    Graph graph;
    mxGraphComponent graphComponent;

    public GraphDisplay(Graph graph) {
        super();
        this.graph = graph;
    }

    public mxGraphComponent initializeAffNoeuds() {
        ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(new DirectedWeightedPseudograph<>(DefaultEdge.class));

        for (Node node : graph.getNodes()) {
            g.addVertex(node.toString());
        }

        for (Node node : graph.getNodes()) {
            for (Link neighbour : node.getAllNeighbours()) {
                g.setEdgeWeight(g.addEdge(node.toString(),
                        neighbour.getNode().toString()), neighbour.getDistance());
            }
        }

        JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);
        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);
        graphComponent.setConnectable(false);
        graphComponent.setEnabled(false);


        mxStylesheet stylesheet = new mxStylesheet();

        Map<String, Object> defStyle = new HashMap<>();
        defStyle.put(mxConstants.STYLE_FONTSIZE, "12");
        defStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        defStyle.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);

        Map<String, Object> highlighted = defStyle;
        highlighted.put(mxConstants.STYLE_LABEL_BORDERCOLOR, "#FFFF00");

        // TODO : ajouter les styles pour les noeuds

        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.setForceConstant(200);
        layout.execute(jgxAdapter.getDefaultParent());

        // Colouring vertices differently following their types
        for (Object vertex : graphComponent.getGraph().getChildVertices(graphComponent.getGraph().getDefaultParent())) {
            String type = (String) graphComponent.getGraph().getModel().getValue(vertex);
            System.out.println(type);
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

        // Make the graph zoomable and scrollable with ctrl-mousewheel
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

        mxCell[] lastSelectedCell = graphComponent.getGraph().getCell; // CHOSE FIRST ELEMENT TO MAKE IT WORK
        Map<String, Object>[] lastSelectedCellStyle = new Map[]{new HashMap<>()};

        graphComponent.getGraphControl().addMouseListener(new mxMouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Check if the mouse is over a cell
                mxCell cell = ((mxCell) graphComponent.getCellAt(e.getX(), e.getY(), false));
                if (cell != null) {
                    //Check if the cell is a vertex
                    if (graphComponent.getGraph().getModel().isVertex(cell)) {
                        String type = (String) graphComponent.getGraph().getModel().getValue(lastSelectedCell[0]);
                        System.out.println(type);
                        if (type.charAt(0) == 'R') {
                            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FF0000", new Object[]{lastSelectedCell[0]});
                            System.out.println("Red");
                        }
                        else if (type.charAt(0) == 'L') {
                            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#00FF00", new Object[]{lastSelectedCell[0]});
                            System.out.println("Green");
                        }
                        else if(type.charAt(0) == 'V'){
                            graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#0000FF", new Object[]{lastSelectedCell[0]});
                            System.out.println("Blue");
                        }
                        graphComponent.getGraph().setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFFF00", new Object[]{cell});
                        System.out.println("Yellow");
                        // TODO: Send the node to the right panel
                        System.out.println(cell.getValue());
                        lastSelectedCell[0] = cell;
                    }
                }
            }
        });

        this.graphComponent = graphComponent;
        return graphComponent;
    }

}
