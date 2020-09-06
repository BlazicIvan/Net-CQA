package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumOfMethodsTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "CommonElements/SingleMethod",
            "NumOfMethods/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("NOM", "CommonElements.BlankIsolated"));
        assertEquals(1,ts.getMetricValue("NOM", "CommonElements.SingleMethod"));
        assertEquals(6, ts.getMetricValue("NOM", "NumOfMethods.C1"));
        assertEquals(2, ts.getMetricValue("NOM", "NumOfMethods.C0"));
    }
}