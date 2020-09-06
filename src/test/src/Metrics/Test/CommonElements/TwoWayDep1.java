package Metrics.Test.CommonElements;

public class TwoWayDep1 {

    static void m1() {
        TwoWayDep2.m1();
    }

}
