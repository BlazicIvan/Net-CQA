package Graph.Export;

import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import org.jgrapht.io.CSVExporter;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.GraphExporter;

public class CSVExportHelper extends GraphExportHelper {

    private final ComponentNameProvider<GraphNode> csvNodeIdProvider = GraphNode::getElementName;

    @Override
    protected GraphExporter<GraphNode, GraphEdge> getExporter() {
        return new CSVExporter(csvNodeIdProvider, CSVFormat.EDGE_LIST,';');
    }

    @Override
    protected String getFileNameEnding() {
        return "_edges.csv";
    }
}
