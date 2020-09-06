package Graph.Export;

import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import org.jgrapht.io.*;

public class DOTExportHelper extends GraphExportHelper {

    @Override
    protected GraphExporter<GraphNode, GraphEdge> getExporter() {
        return new DOTExporter(defaultNodeIdProvider, defaultNodeLabelProvider, defaultEdgeLabelProvider,
                defaultNodeAttributeProvider, defaultEdgeAttributeProvider);
    }

    @Override
    protected String getFileNameEnding() {
        return ".dot";
    }
}
