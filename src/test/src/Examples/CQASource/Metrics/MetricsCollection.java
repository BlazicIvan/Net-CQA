package Metrics;

import Graph.GraphCollection;
import Graph.Structural.*;
import Graph.Node.ClassNode;
import Metrics.Class.*;
import Metrics.System.*;
import Util.Log.Logging;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MetricsCollection {

    private final List<Metric> allMetrics;
    private final List<String> allClassNames;

    private static final Logger logger = Logging.getLogger(MetricsCollection.class);

    private double getResult(Metric metric, boolean log) {
        if(log) {
            logger.info(metric.toString());
            logger.info(metric.getExtraInfoMap().toString());
        }

        return metric.getResult();
    }

    private void addMetric(Metric metric) {
        allMetrics.add(metric);
    }

    public double getResult(String className, String id, boolean log) {
        Metric metric = findById(id, className);
        return getResult(metric, log);
    }

    public double getResult(String id, boolean log) {
        Metric metric = findById(id);
        return getResult(metric, log);
    }

    public void logAllResults() {
        for(Metric m : allMetrics) {
            logger.info(m.toString());
        }
    }

    public Set<String> getAllMetricIds() {
        return allMetrics.stream().map(Metric::getId).collect(Collectors.toSet());
    }

    public Collection<Metric> findAllById(String metricId) {
        return allMetrics.stream().filter(metric -> metric.getId().equals(metricId)).collect(Collectors.toSet());
    }

    public Collection<Metric> findAllById(String metricId, String className) {
        return findAllById(metricId).stream().filter(metric -> metric instanceof ClassMetric)
                .filter(metric -> ((ClassMetric) metric).getFullClassName().equals(className))
                .collect(Collectors.toSet());
    }

    public Metric findById(String id, String className) {
        Optional<Metric> metricsForClass = allMetrics.stream()
                .filter(metric -> metric instanceof ClassMetric)
                .filter(metric -> ((ClassMetric) metric).getFullClassName().equals(className))
                .filter(metric -> metric.getId().equals(id))
                .findFirst();

        return metricsForClass.orElse(null);

    }

    public Metric findById(String id) {
        Optional<Metric> result = allMetrics.stream().filter(metric -> metric.getId().equals(id)).findFirst();

        return result.orElse(null);
    }


    public List<Metric> getAllMetrics() {
        return allMetrics;
    }

    public List<String> getAllClassNames() {
        return allClassNames;
    }

    public Collection<Metric> getAllSystemMetrics() {
        return allMetrics.stream()
                .filter(metric -> !(metric instanceof ClassMetric))
                .collect(Collectors.toSet());
    }

    public Collection<Metric> getAllMetricsForClass(String className) {
        return allMetrics.stream()
                .filter(metric -> metric instanceof ClassMetric)
                .filter(metric -> (className != null) && className.equals(((ClassMetric) metric).getFullClassName()))
                .collect(Collectors.toSet());
    }

    public MetricsCollection(GraphCollection collection) {

        allMetrics = new ArrayList<>();
        allClassNames = new ArrayList<>();
        
        ClassCodeGraph classGraph = collection.getClassCodeGraph();
        TypeDependencyCodeGraph typeDependencyGraph = collection.getTypeDependencyCodeGraph();
        CallCodeGraph callCodeGraph = collection.getCallCodeGraph();

        for (ClassNode classNode : classGraph.getClassNodes()) {
            addMetric(new NumOfMemberVars(classGraph,  classNode));
            addMetric(new NumOfMethods(classGraph, classNode));
            addMetric(new NumOfSubclasses(typeDependencyGraph, classNode));
            addMetric(new NumOfImplInterfaces(typeDependencyGraph, classNode));
            addMetric(new DecouplingImpact(typeDependencyGraph, classNode));
            addMetric(new TightClassCohesion(classGraph, classNode));
            addMetric(new CouplingBetweenObjects(typeDependencyGraph, classNode));
            addMetric(new InterfaceSize(classGraph, classNode));
            addMetric(new ResponseForClass(classGraph, callCodeGraph, classNode));
            addMetric(new WeightedMethodCount(classGraph, classNode));

            allClassNames.add(classNode.getQualifiedName());
        }

        addMetric(new DegreeOfInterDep(typeDependencyGraph));
        addMetric(new NumOfCyclicDeps(typeDependencyGraph));
        addMetric(new NumOfDisconnectedGroups(typeDependencyGraph));
        addMetric(new DepthOfInheritanceTree(typeDependencyGraph));
        addMetric(new MaxCallIndirection(callCodeGraph));
    }

}
