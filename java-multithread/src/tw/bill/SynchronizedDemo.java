package tw.bill;

import java.util.List;

public class SynchronizedDemo {
    private static int value1;
    private int value2;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public synchronized void demo1() {
        System.out.println(value2);
    }

    public synchronized void demo2() {
        System.out.println(value1);
    }

    public void demo3() {
        synchronized (this) {
            System.out.println(value2);
        }
    }

    public static void demo4() {
        synchronized (SynchronizedDemo.class) {
            System.out.println(value1);
        }
    }

    public void demo5() {
        synchronized (lock1) {
            System.out.println(value1);
        }
    }

    public void demo6() {
        synchronized (lock2) {
            System.out.println(value1);
        }
    }
}
