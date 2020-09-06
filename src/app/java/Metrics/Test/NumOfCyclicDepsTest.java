package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumOfCyclicDepsTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "CommonElements/BlankIsolated",
            "CommonElements/SingleDep1",
            "CommonElements/SingleDep2",
            "CommonElements/TwoWayDep1",
            "CommonElements/TwoWayDep2",
            "NumOfCyclicDeps/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(6, ts.getMetricValue("NCDC"));
    }
}