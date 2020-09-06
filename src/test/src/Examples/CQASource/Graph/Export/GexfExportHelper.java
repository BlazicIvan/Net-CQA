package Graph.Export;

import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import Util.Log.Logging;
import org.gephi.appearance.api.*;
import org.gephi.appearance.plugin.PartitionElementColorTransformer;
import org.gephi.appearance.plugin.RankingNodeSizeTransformer;
import org.gephi.graph.api.Column;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.spi.CharacterExporter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.importer.plugin.file.ImporterDOT;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.Degree;
import org.jgrapht.Graph;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.openide.util.Lookup;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.logging.Logger;

public class GexfExportHelper extends GraphExportHelper {


    @Override
    protected GraphExporter<GraphNode, GraphEdge> getExporter() {

        return new GraphExporter<GraphNode, GraphEdge>() {

            @Override
            public void exportGraph(Graph<GraphNode, GraphEdge> graph, Writer writer) throws ExportException {
                try {

                    ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
                    pc.newProject();
                    Workspace workspace = pc.getCurrentWorkspace();

                    Logging.disableLogger(Logger.getLogger(getClass().getSimpleName()));

                    GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
                    DirectedGraph directedGraph = graphModel.getDirectedGraph();

                    ByteArrayOutputStream dotOutputStream = new ByteArrayOutputStream();
                    GraphExportHelper exportHelper = new DOTExportHelper();
                    exportHelper.export(dotOutputStream, graph);

                    InputStream dotInputStream = new ByteArrayInputStream(dotOutputStream.toByteArray());

                    ImportController importController = Lookup.getDefault().lookup(ImportController.class);
                    Container container = importController.importFile(dotInputStream, new ImporterDOT());
                    container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);
                    importController.process(container, new DefaultProcessor(), workspace);

                    AppearanceController appearanceController = Lookup.getDefault().lookup(AppearanceController.class);
                    AppearanceModel appearanceModel = appearanceController.getModel();

                    Degree degree = new Degree();
                    degree.execute(graphModel);

                    Column degreeColumn = graphModel.getNodeTable().getColumn(Degree.DEGREE);
                    Function degreeRanking = appearanceModel.getNodeFunction(directedGraph,
                            degreeColumn,
                            RankingNodeSizeTransformer.class);
                    RankingNodeSizeTransformer degreeTransformer = degreeRanking.getTransformer();
                    degreeTransformer.setMinSize(1);
                    degreeTransformer.setMaxSize(2);
                    appearanceController.transform(degreeRanking);

                    Column column = graphModel.getNodeTable().getColumn("type");
                    Function func = appearanceModel.getNodeFunction(directedGraph, column, PartitionElementColorTransformer.class);
                    Partition partition = ((PartitionFunction) func).getPartition();

                    partition.setColor("class", Color.BLUE);
                    partition.setColor("method", Color.ORANGE);
                    partition.setColor("field", Color.GREEN);
                    partition.setColor("interface", Color.YELLOW);
                    partition.setColor("enum", Color.GRAY);
                    appearanceController.transform(func);


                    ForceAtlas2 forceAtlas2 = new ForceAtlas2Builder().buildLayout();
                    forceAtlas2.setGraphModel(graphModel);
                    forceAtlas2.setThreadsCount(3);
                    forceAtlas2.setJitterTolerance(1.0);
                    forceAtlas2.setScalingRatio(1.0);
                    forceAtlas2.setStrongGravityMode(true);
                    forceAtlas2.setGravity(0.5);


                    forceAtlas2.initAlgo();
                    for(int i=0; i<100 && forceAtlas2.canAlgo(); i++) {
                        forceAtlas2.goAlgo();
                    }
                    forceAtlas2.endAlgo();


                    ExportController ec = Lookup.getDefault().lookup(ExportController.class);
                    Exporter exporter = ec.getExporter("gexf");
                    CharacterExporter characterExporter = (CharacterExporter)exporter;

                    ec.exportWriter(writer,characterExporter);

                } catch (Exception ex) {
                    throw new ExportException(ex.getMessage());
                }
            }
        };
    }

    @Override
    protected String getFileNameEnding() {
        return ".gexf";
    }
}
