package Graph;

import CodeModel.CodeModel;
import Graph.Export.GraphExportHelper;
import Graph.Structural.*;
import org.jgrapht.io.ExportException;

import java.io.IOException;

public class GraphCollection {

    private final TypeDependencyCodeGraph typeDependencyCodeGraph;
    private final ClassCodeGraph classCodeGraph;
    private final UnifiedCodeGraph unifiedCodeGraph;
    private final CallCodeGraph callCodeGraph;

    public GraphCollection(CodeModel model) {
        typeDependencyCodeGraph = new TypeDependencyCodeGraph( "TypeDependencyGraph", model);
        classCodeGraph = new ClassCodeGraph("ClassStructureGraph", model);
        callCodeGraph = new CallCodeGraph("CallGraph", model);
        unifiedCodeGraph = new UnifiedCodeGraph("UnifiedGraph", model);
    }

    public TypeDependencyCodeGraph getTypeDependencyCodeGraph() {
        return typeDependencyCodeGraph;
    }

    public ClassCodeGraph getClassCodeGraph() {
        return classCodeGraph;
    }

    public CallCodeGraph getCallCodeGraph() {
        return callCodeGraph;
    }

    public void exportAll(String path, GraphExportHelper exportHelper) throws IOException, ExportException {
        typeDependencyCodeGraph.export(path,exportHelper);
        classCodeGraph.export(path,exportHelper);
        unifiedCodeGraph.export(path,exportHelper);
        callCodeGraph.export(path, exportHelper);
    }
}
