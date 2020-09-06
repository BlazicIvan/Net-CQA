package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumOfImplInterfacesTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "NumOfImplInterfaces/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(1, ts.getMetricValue("NII", "NumOfImplInterfaces.C1"));
        assertEquals(0, ts.getMetricValue("NII", "NumOfImplInterfaces.C2"));
        assertEquals(2, ts.getMetricValue("NII", "NumOfImplInterfaces.C3"));
        assertEquals(0, ts.getMetricValue("NII", "CommonElements.BlankIsolated"));

    }
}