package util;

public class MyRandomThread extends Thread {
    private String msg;

    public MyRandomThread() {
    }

    public MyRandomThread(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Util.randomSymbolResults.add(msg);
    }
}
