package Graph.Export;

import CodeModel.Dependencies.Dependency;
import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import Util.FileHelper;
import org.jgrapht.Graph;
import org.jgrapht.io.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class GraphExportHelper {

    final ComponentNameProvider<GraphNode> defaultNodeIdProvider = GraphNode::getId;
    final ComponentNameProvider<GraphNode> defaultNodeLabelProvider = GraphNode::getElementName;
    final ComponentNameProvider<GraphEdge> defaultEdgeIdProvider = GraphEdge::getId;
    final ComponentNameProvider<GraphEdge> defaultEdgeLabelProvider = GraphEdge::getLabel;



    protected final ComponentAttributeProvider<GraphNode> defaultNodeAttributeProvider = new ComponentAttributeProvider<GraphNode>() {
        @Override
        public Map<String, Attribute> getComponentAttributes(GraphNode node) {
            Map<String, Attribute> stringAttributeMap = new HashMap<>();

            stringAttributeMap.put("Type", new DefaultAttribute<>(node.getTypeName(),
                    AttributeType.STRING));

            stringAttributeMap.put("Access", new DefaultAttribute<>(node.getAccessModifierString(),
                    AttributeType.STRING));

            return stringAttributeMap;
        }
    };

    protected final ComponentAttributeProvider<GraphEdge> defaultEdgeAttributeProvider = new ComponentAttributeProvider<GraphEdge>() {
        @Override
        public Map<String, Attribute> getComponentAttributes(GraphEdge graphEdge) {
            Map<String, Attribute> stringAttributeMap = new HashMap<>();

            for(Dependency dependency : graphEdge.getDependencies()) {
                stringAttributeMap.put(dependency.getRelationDescription(), new DefaultAttribute<>(true,
                        AttributeType.BOOLEAN));
            }

            stringAttributeMap.put("Weight", new DefaultAttribute<>(graphEdge.getWeight(), AttributeType.DOUBLE));

            return stringAttributeMap;
        }
    };

    protected abstract GraphExporter<GraphNode,GraphEdge> getExporter();
    protected abstract String getFileNameEnding();

    public void export(String path, String name, Graph<GraphNode, GraphEdge> graph) throws IOException, ExportException {
        if(FileHelper.checkAndCreateDirectory(path)) {

            String fileName = FileHelper.appendPath(path, name + getFileNameEnding());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);

            getExporter().exportGraph(graph, writer);

            writer.close();
            fileOutputStream.close();
        }
    }

    public void export(OutputStream outputStream, Graph<GraphNode, GraphEdge> graph) throws ExportException {
        getExporter().exportGraph(graph, outputStream);
    }
}
