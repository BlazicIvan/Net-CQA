package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterfaceSizeTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
        "CommonElements/SingleMethod",
        "CommonElements/BlankIsolated",
        "InterfaceSize/*",
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("IS", "CommonElements.BlankIsolated"));
        assertEquals(1, ts.getMetricValue("IS", "CommonElements.SingleMethod"));
        assertEquals(6, ts.getMetricValue("IS", "InterfaceSize.C2"));
    }
}