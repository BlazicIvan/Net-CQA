package Metrics.Test.ResponseForClass;

public class C1 {

    private C2 c2 = new C2();

    public void m1() {
        m4();
    }

    public void m2() {
        c2.m1();
        c2.m2();
    }

    public void m3() {
        c2.m2();
    }

    public void m4() {}
}
