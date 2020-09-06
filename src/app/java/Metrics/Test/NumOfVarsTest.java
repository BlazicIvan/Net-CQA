package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumOfVarsTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "NumOfVars/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("NOVF", "CommonElements.BlankIsolated"));
        assertEquals(2, ts.getMetricValue("NOVF", "NumOfVars.C2"));
        assertEquals(1, ts.getMetricValue("NOVF", "NumOfVars.C3"));
    }
}