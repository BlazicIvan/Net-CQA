package Graph;

import CodeModel.CodeModel;
import Graph.Edge.EdgeFilter;
import Graph.Edge.GraphEdge;
import Graph.Export.GraphExportHelper;
import Graph.Node.GraphNode;
import Graph.Node.NodeFilter;
import Util.Log.Logging;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.JohnsonShortestPaths;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.io.ExportException;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public abstract class CodeGraph {

    private final Logger logger = Logging.getLogger(CodeGraph.class);

    private final String name;
    protected final org.jgrapht.Graph<GraphNode, GraphEdge> graph;
    protected final CodeModel model;

    public enum EdgeDirection {
        ANY,
        IN,
        OUT
    }

    protected abstract void buildGraph();

    protected CodeGraph(String name, CodeModel model) {
            graph = new DefaultDirectedWeightedGraph<>(GraphEdge.class);
            this.name = name;
            this.model = model;
            buildGraph();
    }

    protected CodeGraph(org.jgrapht.Graph<GraphNode, GraphEdge> graph) {
        this.name = "anonymous_graph";
        this.model = null;
        this.graph = graph;
    }

    public Set<GraphNode> getNeighboursByFilter(EdgeDirection direction, GraphNode node, NodeFilter nodeFilter) {

        return getNeighboursByFilter(direction, node, (EdgeFilter) edge -> {

            GraphNode targetNode = graph.getEdgeTarget(edge);
            GraphNode sourceNode = graph.getEdgeSource(edge);

            boolean inputNeighbourAccepted = (targetNode.equals(node) && nodeFilter.acceptNode(sourceNode));
            boolean outputNeighbourAccepted = (sourceNode.equals(node) && nodeFilter.acceptNode(targetNode));


            return inputNeighbourAccepted || outputNeighbourAccepted;
        });
    }


    public Set<GraphNode> getNeighboursByFilter(EdgeDirection direction, GraphNode node, EdgeFilter filter) {
        Set<GraphNode> result = new HashSet<>();

        Set<GraphEdge> edges = graph.edgesOf(node);

        for(GraphEdge edge : edges) {
            GraphNode targetNode = graph.getEdgeTarget(edge);
            GraphNode sourceNode = graph.getEdgeSource(edge);

            if (targetNode == null || sourceNode == null) {
                continue;
            }

            boolean edgeAccepted = filter.acceptEdge(edge);

            boolean inputNeighbourAccepted = (edgeAccepted && targetNode.equals(node));
            boolean outputNeighbourAccepted = (edgeAccepted && sourceNode.equals(node));

            if((direction == EdgeDirection.IN || direction == EdgeDirection.ANY ) && inputNeighbourAccepted) {
                result.add(sourceNode);
            }

            if ((direction == EdgeDirection.OUT || direction == EdgeDirection.ANY ) && outputNeighbourAccepted) {
                result.add(targetNode);
            }
        }

        return result;
    }

    public Set<GraphEdge> getEdgesByFilter(EdgeDirection direction, GraphNode node, NodeFilter neighbourFilter) {
        Set<GraphEdge> edges = new HashSet<>();

        for(GraphEdge edge : graph.edgesOf(node)) {
            GraphNode targetNode = graph.getEdgeTarget(edge);
            GraphNode sourceNode = graph.getEdgeSource(edge);

            boolean inputNeighbourAccepted = (targetNode.equals(node) && neighbourFilter.acceptNode(sourceNode));
            boolean outputNeighbourAccepted = (sourceNode.equals(node) && neighbourFilter.acceptNode(targetNode));

            if (((direction == EdgeDirection.IN || direction == EdgeDirection.ANY) && inputNeighbourAccepted) ||
                ((direction == EdgeDirection.OUT || direction == EdgeDirection.ANY) && outputNeighbourAccepted)) {
                edges.add(edge);
            }
        }

        return edges;
    }

    public int getDegreeByFilter(EdgeDirection direction, GraphNode node, NodeFilter nodeFilter) {
        return getNeighboursByFilter(direction, node, nodeFilter).size();
    }

    public int getNodeDegree(GraphNode node, EdgeDirection edgeDirection) {
        return getDegreeByFilter(edgeDirection, node, node1 -> true);
    }

    public Set<GraphNode> findCycles() {
        CycleDetector<GraphNode,GraphEdge> cycleDetector = new CycleDetector<>(graph);
        return cycleDetector.findCycles();
    }

    public Set<Set<GraphNode>> getWeaklyConnectedComponents() {
        Set<Set<GraphNode>> components = new HashSet<>();
        BiconnectivityInspector<GraphNode, GraphEdge> biconnectivityInspector = new BiconnectivityInspector<>(graph);
        Set<org.jgrapht.Graph<GraphNode,GraphEdge>> subGraphs = biconnectivityInspector.getConnectedComponents();

        for(org.jgrapht.Graph<GraphNode,GraphEdge> subGraph : subGraphs) {
            components.add(subGraph.vertexSet());
        }

        return components;
    }

    public Set<GraphNode> getOutgoingEdgeOnlyNodes() {
        Set<GraphNode> nodeSet = new HashSet<>();

        for(GraphNode node : graph.vertexSet()) {
            if(getNodeDegree(node, EdgeDirection.IN) == 0 && getNodeDegree(node, EdgeDirection.OUT) > 0) {
                nodeSet.add(node);
            }
        }

        return nodeSet;
    }

    public Set<GraphNode> getIncomingEdgeOnlyNodes() {
        Set<GraphNode> nodeSet = new HashSet<>();

        for(GraphNode node : graph.vertexSet()) {
            if(getNodeDegree(node, EdgeDirection.IN) > 0 && getNodeDegree(node, EdgeDirection.OUT) == 0) {
                nodeSet.add(node);
            }
        }

        return nodeSet;
    }

    public List<GraphPath<GraphNode,GraphEdge>> getAllSingleSourcePaths(GraphNode source, Set<GraphNode> destinations) {
        ShortestPathAlgorithm.SingleSourcePaths<GraphNode,GraphEdge> singleSourcePaths =
                new JohnsonShortestPaths<>(graph).getPaths(source);

        List<GraphPath<GraphNode,GraphEdge>> pathList = new LinkedList<>();
        for(GraphNode dest : destinations) {
            pathList.add(singleSourcePaths.getPath(dest));
        }

        return pathList;
    }

    public Set<GraphNode> getNodes() {
        return graph.vertexSet();
    }

    public Set<GraphEdge> getEdges() {
        return graph.edgeSet();
    }

    public double getNumberOfPathsThroughNode(GraphNode node) {
        try {
            BetweennessCentrality<GraphNode, GraphEdge> betweennessCentrality = new BetweennessCentrality<>(graph,
                    false);
            return betweennessCentrality.getVertexScore(node);
        }
        catch (IllegalArgumentException e) {
            logger.severe("Error in finding betweenness centrality: "+node.getElementName());
            logger.severe(e.toString());
            return 0;
        }
    }

    public GraphPath<GraphNode, GraphEdge> getLongestDirectedPath() {
        GraphPath<GraphNode, GraphEdge> longestPath = null;

        AllDirectedPaths<GraphNode, GraphEdge> allDirectedPaths = new AllDirectedPaths<>(graph);
        Set<GraphNode> allVertecies = graph.vertexSet();

        List<GraphPath<GraphNode, GraphEdge>> allPaths = allDirectedPaths.getAllPaths(allVertecies, allVertecies,
                true, null);
        Optional<GraphPath<GraphNode, GraphEdge>> optional =
                allPaths.stream().max(Comparator.comparing(GraphPath::getLength));

        if(optional.isPresent()) {
            longestPath = optional.get();
        }

        return longestPath;
    }

    public CodeGraph createProjection(EdgeFilter filter) {
        org.jgrapht.Graph<GraphNode, GraphEdge> extractedGraph = new DefaultDirectedWeightedGraph<>(GraphEdge.class);
        for (GraphEdge edge : getEdges()) {
            if(filter.acceptEdge(edge)) {
                GraphNode source = edge.getSource();
                GraphNode target = edge.getTarget();

                extractedGraph.addVertex(source);
                extractedGraph.addVertex(target);
                extractedGraph.addEdge(source, target, edge);
            }
        }

        return new CodeGraph(extractedGraph) {
            @Override
            protected void buildGraph() {}
        };
    }

    public String getName() {
        return name;
    }

    public double getDensity() {
        double numOfNodes = (double)graph.vertexSet().size();
        double numOfEdges = (double)graph.edgeSet().size();

        double result = 0;

        if (numOfNodes > 1) {
            result = numOfEdges/(numOfNodes*(numOfNodes-1));
        }

        return result;
    }

    public void export(String path, GraphExportHelper exportHelper) throws IOException, ExportException {
        exportHelper.export(path, name, graph);
    }

}
