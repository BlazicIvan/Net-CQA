package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CouplingBetweenObjectsTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "CouplingBetweenObjects/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("CBO", "CommonElements.BlankIsolated"));
        assertEquals(2, ts.getMetricValue("CBO", "CouplingBetweenObjects.C2"));
        assertEquals(1, ts.getMetricValue("CBO", "CouplingBetweenObjects.C3"));
        assertEquals(0, ts.getMetricValue("CBO", "CouplingBetweenObjects.C4"));
        assertEquals(0, ts.getMetricValue("CBO", "CouplingBetweenObjects.C5"));
    }
}