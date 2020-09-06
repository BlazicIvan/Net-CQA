package Grade;

import Configuration.ConfigurationManager;
import Metrics.Metric;
import Metrics.MetricsCollection;
import Util.AverageCalculator;
import Util.MathUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GradeOverview {

    private final Map<String,Property> propertyMap;
    private final MetricsCollection metricsCollection;
    private Double finalGrade;
    private Double maxGrade;


    public GradeOverview(MetricsCollection metricsCollection, double maxGrade) {
        this.metricsCollection = metricsCollection;
        this.maxGrade = maxGrade;
        this.propertyMap = ConfigurationManager.getInstance().getDefaultProperties();
    }

    public double getFinalGrade() {
        if (finalGrade == null) {
            AverageCalculator averageCalculator = new AverageCalculator();
            for(Property property : getAllProperties()) {
                averageCalculator.addElement(getPropertyGrade(property));
            }
            finalGrade = averageCalculator.getAverage();
        }

        return MathUtils.roundValue(finalGrade,3);
    }

    public Collection<Property> getAllProperties() {
        return propertyMap.values();
    }

    public double getPropertyGrade(Property property) {
        return property.getGrade(metricsCollection, maxGrade);
    }

    public List<String> getAllMetricIds() {
        return new ArrayList<>(metricsCollection.getAllMetricIds());
    }

    public Collection<Metric> getAllMetricsForClass(String className) {
        return metricsCollection.getAllMetricsForClass(className);
    }

    public List<String> getAllClassNames() {
        return metricsCollection.getAllClassNames();
    }

    public Collection<Metric> getAllSystemMetrics() {
        return metricsCollection.getAllSystemMetrics();
    }

    public Double getMaxGrade() {
        return maxGrade;
    }
}
