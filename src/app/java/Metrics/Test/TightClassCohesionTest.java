package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TightClassCohesionTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "TightClassCohesion/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("TCC", "CommonElements.BlankIsolated"));
        assertEquals(0, ts.getMetricValue("TCC", "TightClassCohesion.C2")); // Methods outside current class considered...
        assertEquals(0.5, ts.getMetricValue("TCC", "TightClassCohesion.C4"));
        assertEquals(1, ts.getMetricValue("TCC", "TightClassCohesion.C5"));

    }
}