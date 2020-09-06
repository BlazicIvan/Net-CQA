package Metrics;

import Configuration.ConfigurationManager;
import Graph.Node.GraphNode;
import Graph.Structural.StructuralCodeGraph;
import Util.CsvStringBuilder;
import Util.MathUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Metric {

    private final Map<String, String> extraInfoMap;
    private Double result;
    protected final StructuralCodeGraph graph;


    protected abstract double calculate();

    protected Metric(StructuralCodeGraph graph) {
        this.graph = graph;
        this.result = null;
        this.extraInfoMap = new HashMap<>();
    }

    public abstract String getId();

    protected final void addExtraInfoNodeList(String key, Collection<GraphNode> nodes) {
        if(!nodes.isEmpty()) {
            CsvStringBuilder csvStringBuilder = new CsvStringBuilder();
            for (GraphNode node : nodes) {
                csvStringBuilder.addItem(node.getElementName());
            }

            extraInfoMap.put(key, csvStringBuilder.toString());
        }
    }

    protected final void addExtraInfoString(String key, String info) {
        if(!info.isEmpty()) {
            extraInfoMap.put(key, info);
        }
    }

    public final String getName() {
        return ConfigurationManager.getInstance().getMetricText(getId()).getTitle();
    }


    public Map<String, String> getExtraInfoMap() {
        getResult();
        return extraInfoMap;
    }

    public String toString() {
        return getName() + " = " + getResult();
    }

    public double getResult() {
        if(result == null) {
            result = MathUtils.roundValue(calculate());
        }
        return result;
    }

}
