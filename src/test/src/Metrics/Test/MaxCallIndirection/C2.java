package Metrics.Test.MaxCallIndirection;

public class C2 {

    void m1() {
        m2();
    }

    void m2() {
        m3();
    }

    void m3() {
        m4();
        m1();
    }

    void m4() {
        m5();
    }

    void m5() {
        m6();
    }

    void m6() {}

}
