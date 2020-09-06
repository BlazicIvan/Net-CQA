package Metrics.Test.NumOfMethods;

public class C1 extends C0 implements I1 {

    public C1() {}

    public void m1() {
        new I1() {
            @Override
            public void m6() {
                m1();
            }
        };
    }

    protected void m2() {}

    private void m3() {}

    public static void m4() {}

    @Override
    public void m5() { }

    @Override
    public void m6() {

    }
}
