package Metrics.Test.WeightedMethodCount;

public class C3 {

    // Cyclo = 2
    void m1(int a) {
        if (a > 0) {
            a = 0;
        }
    }

    // Cyclo = 4
    void m2(int a) {
        try {
            do {
                a--;
                continue;
            } while (a>0);
        } catch (Exception e) {
            a = 0;
        }
    }

    // Cyclo = 10
    void m3() {
        int x = 0, y = 2, t = 0;
        boolean a = false, b = true;
        boolean d = false;

        if (a && (y == 1 ? b : true)) {
            if (y == x) {
                while (true) {
                    if (x++ < 20) {
                        break;
                    }
                }
            } else if (y == t && !d) {
                x = a ? y : x;
            } else {
                x = 2;
            }
        }
    }

}
