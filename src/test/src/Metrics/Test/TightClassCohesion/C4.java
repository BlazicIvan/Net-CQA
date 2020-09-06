package Metrics.Test.TightClassCohesion;

public class C4 {

    private int v1;
    private int v2;
    private int v3;

    public void m1() {
        v1 = 0;
    }

    public void m2() {
        v1 = 0;
    }

    private void m3() {
        v2 = 0;
    }

    public void m4() {
        v1 = 0;
        v2 = 0;
    }


    public void m5() {}

}
