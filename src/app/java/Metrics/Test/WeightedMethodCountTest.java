package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightedMethodCountTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "CommonElements/SingleMethod",
            "WeightedMethodCount/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("WMC", "CommonElements.BlankIsolated"));
        assertEquals(1, ts.getMetricValue("WMC", "CommonElements.SingleMethod"));
        assertEquals(16, ts.getMetricValue("WMC", "WeightedMethodCount.C3"));
    }
}