package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseForClassTest {

    private static MetricTestSetup ts;
    private static final String[] includedFiles = {
            "ResponseForClass/*"
    };

    @BeforeAll
    static void setup() {
        ts = new MetricTestSetup(includedFiles);
    }


    @Test
    void TestC1() {
        assertEquals(6, ts.getMetricValue("RFC", "ResponseForClass.C1"));
    }

    @Test
    void TestC2() {
        assertEquals(4, ts.getMetricValue("RFC", "ResponseForClass.C2"));
    }

    @Test
    void TestC3() {
        assertEquals(2, ts.getMetricValue("RFC", "ResponseForClass.C3"));
    }
}