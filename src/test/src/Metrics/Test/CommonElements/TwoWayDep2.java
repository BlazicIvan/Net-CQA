package Metrics.Test.CommonElements;

public class TwoWayDep2 {

    static void m1() {
        TwoWayDep1.m1();
    }

}
