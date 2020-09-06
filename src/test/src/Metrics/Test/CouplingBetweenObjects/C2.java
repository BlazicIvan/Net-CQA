package Metrics.Test.CouplingBetweenObjects;

public class C2 extends C5 {

    private C3 v1 = new C3();

    void m1() {
        v1.m1();
    }

    void m2() {
        C4.m1();
    }

}
