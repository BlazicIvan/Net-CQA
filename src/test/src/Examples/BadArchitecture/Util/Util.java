package Examples.BadArchitecture.Util;


import Examples.BadArchitecture.Data.DataStore;

public class Util {

    public static String DEFAULT_WORKING_DIR = DataStore.getDefaultWorkingDir();

    public static void printLine(String line) {
        System.out.println(line);
    }

    public static double calculate(double a, double b) {
        double c = 10;
        double x,y;

        x = a*c;
        y = b*x;

        return x+y;
    }

    public static void baseCyclo() {                // Cyclo = 1
        highCyclo();
    }

    public static void highCyclo() {                // Cyclo = 10
        int x = 0, y = 2, t = 0;
        boolean a = false, b = true;
		boolean d = false;

        if (a && (y == 1 ? b : true)) { // +3
            if (y == x) {                 // +1
                while (true) {              // +1
                    if (x++ < 20) {           // +1
                        break;                  // +1
                    }
                }
            } else if (y == t && !d) {    // +2
                x = a ? y : x;              // +1
            } else {
                x = 2;
            }
        }
		
		calculate(0,0);
    }

    public void f1() {
        f2();
    }

    public void f2() {
        f3();
    }

    public void f3() {
        f4();
    }

    public void f4() {
        f5();
    }

    public void f5() {
        f6();
    }

    public void f6() { f7(); }

    public void f7() {
        f8();
    }

    public void f8() {
        f9();
    }

    public void f9() {
        f10();
    }


    public void f10() {
        highCyclo();
        DataStore.getInstance();
    }
}
