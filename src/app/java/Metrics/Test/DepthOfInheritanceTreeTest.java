package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepthOfInheritanceTreeTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
        "CommonElements/BlankIsolated",
        "CommonElements/OneInheritanceBase",
        "CommonElements/OneInheritanceDerived",
        "DepthOfInheritanceTree/*",
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(3, ts.getMetricValue("DIT"));
    }
}