package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumOfSubclassesTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
        "CommonElements/BlankIsolated",
        "CommonElements/OneInheritanceBase",
        "CommonElements/OneInheritanceDerived",
        "CommonElements/TwoInheritanceBase",
        "CommonElements/TwoInheritanceDerived1",
        "CommonElements/TwoInheritanceDerived2",
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }

    @Test
    void calculate() {
        assertEquals(0, ts.getMetricValue("NSUB", "CommonElements.BlankIsolated"));
        assertEquals(1, ts.getMetricValue("NSUB", "CommonElements.OneInheritanceBase"));
        assertEquals(0, ts.getMetricValue("NSUB", "CommonElements.OneInheritanceDerived"));
        assertEquals(2, ts.getMetricValue("NSUB", "CommonElements.TwoInheritanceBase"));
        assertEquals(0, ts.getMetricValue("NSUB", "CommonElements.TwoInheritanceDerived1"));
        assertEquals(0, ts.getMetricValue("NSUB", "CommonElements.TwoInheritanceDerived1"));

    }
}