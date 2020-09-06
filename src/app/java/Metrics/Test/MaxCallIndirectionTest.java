package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxCallIndirectionTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
        "CommonElements/BlankIsolated",
        "MaxCallIndirection/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(5, ts.getMetricValue("MCI"));
    }
}