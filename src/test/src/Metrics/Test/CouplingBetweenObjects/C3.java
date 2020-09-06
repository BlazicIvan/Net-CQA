package Metrics.Test.CouplingBetweenObjects;

public class C3 {

    String m1() {
        return "";
    }

    void m2() {
        C4.m1();
    }
}
