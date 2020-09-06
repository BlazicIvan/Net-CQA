package Examples.BadArchitecture.Util;

public class Runner extends Thread {

    public void start() {
        threadProcess(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void threadProcess(Runnable runnable) {
        runnable.run();
    }
}
