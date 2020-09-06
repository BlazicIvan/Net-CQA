package Grade;


import Configuration.ConfigurationManager;
import Grade.MetricGradeCalculations.MetricGradeCalculator;
import Metrics.Class.ClassMetric;
import Metrics.Metric;
import Metrics.MetricsCollection;
import Util.AverageCalculator;
import Util.MathUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Property {

    private final String propertyId;
    private final String name;
    private final Map<String, MetricFactor> metricFactorMap;
    private Double grade;

    public Property(String propertyId,
                    String name,
                    Map<String, MetricFactor> metricFactorMap) {
        this.propertyId = propertyId;
        this.name = name;
        this.metricFactorMap = metricFactorMap;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getName() {
        return name;
    }

    MetricFactor getMetricFactor(String metricId) {
        if (metricFactorMap.containsKey(metricId)) {
            return metricFactorMap.get(metricId);
        }

        return new MetricFactor("No influence", 0);
    }

    public List<MetricFactor> getAllMetricFactors() {
        return new ArrayList<>(metricFactorMap.values());
    }

    double getMetricRelativeGrade(String metricId, MetricsCollection metricsCollection) {
        double grade;
        Map<String, MetricGradeCalculator> calculatorMap = ConfigurationManager.getInstance().getDefaultCalculatorMap();
        Metric metric = metricsCollection.findById(metricId);
        if(metric instanceof ClassMetric) {
            AverageCalculator classAverage = new AverageCalculator();
            Collection<Metric> allClasses =  metricsCollection.findAllById(metricId);
            for(Metric singleClassMetric : allClasses) {
                double relativeGrade = calculatorMap.get(metricId)
                        .calculateRelativeGrade(singleClassMetric.getResult());
                classAverage.addElement(relativeGrade);
            }
            grade = classAverage.getAverage();
        }
        else {
            grade = calculatorMap.get(metricId).calculateRelativeGrade(metric.getResult());
        }

        return MathUtils.roundValue(grade);
    }


    double getGrade(MetricsCollection metricsCollection, double maxGrade) {
        if (grade == null) {
            double value = 0;
            for (String metricId : metricsCollection.getAllMetricIds()) {
                double metricFactor = getMetricFactor(metricId).getValue();
                if(metricFactor > 0) {
                    double metricGrade = getMetricRelativeGrade(metricId, metricsCollection) * maxGrade;
                    value += metricFactor * metricGrade;
                }
            }

            grade = value;
        }

        return MathUtils.roundValue(grade);
    }
}
