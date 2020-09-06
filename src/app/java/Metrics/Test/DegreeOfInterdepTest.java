package Metrics.Test;

import Metrics.Test.Setup.MetricTestSetup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DegreeOfInterdepTest {

    private double performTestCase(String caseName) {
        String[] files = {"DegreeOfInterdep/" + caseName + "/*"};

        MetricTestSetup ts = new MetricTestSetup(files);

        return ts.getMetricValue("DOI");
    }

    @Test
    void testA() {
        assertEquals(0, performTestCase("CaseA"));
    }

    @Test
    void testB() {
        assertEquals(0.5, performTestCase("CaseB"));
    }

    @Test
    void testC() {
        assertEquals(1, performTestCase("CaseC"));
    }

    @Test
    void testD() {
        assertEquals(0, performTestCase("CaseD"));
    }

    @Test
    void testE() {
        assertEquals(0.33, performTestCase("CaseE"));
    }
}