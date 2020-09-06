package Graph.Export;

import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import org.jgrapht.io.GmlExporter;
import org.jgrapht.io.GraphExporter;

public class GMLExportHelper extends GraphExportHelper {

    @Override
    protected GraphExporter<GraphNode, GraphEdge> getExporter() {
        GmlExporter<GraphNode,GraphEdge> exporter = new GmlExporter<>(defaultNodeIdProvider, defaultNodeLabelProvider,
                defaultEdgeIdProvider, defaultEdgeLabelProvider);

        exporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
        exporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS, true);

        return exporter;
    }

    @Override
    protected String getFileNameEnding() {
        return ".gml";
    }
}
