package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecouplingImpactTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "DecouplingImpact/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }


    @Test
    void TestC1() {
        assertEquals(6, ts.getMetricValue("DI", "DecouplingImpact.C1"));

    }

    @Test
    void TestC2() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C2"));

    }

    @Test
    void TestC3() {
        assertEquals(1, ts.getMetricValue("DI", "DecouplingImpact.C3"));

    }

    @Test
    void TestC4() {
        assertEquals(3, ts.getMetricValue("DI", "DecouplingImpact.C4"));

    }

    @Test
    void TestC5() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C5"));

    }

    @Test
    void TestC6() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C6"));

    }

    @Test
    void TestC7() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C7"));

    }

    @Test
    void TestC8() {
        assertEquals(2, ts.getMetricValue("DI", "DecouplingImpact.C8"));

    }

    @Test
    void TestC9() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C9"));

    }

    @Test
    void TestC10() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C10"));

    }

    @Test
    void TestC11() {
        assertEquals(0, ts.getMetricValue("DI", "DecouplingImpact.C11"));
    }
}