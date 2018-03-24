package tw.bill;



class Demo1 extends Thread {
    @Override
    public void run() {
        System.out.println("Demo1");
    }
}

class Demo2 implements Runnable {
    @Override
    public void run() {
        System.out.println("Demo2");
    }
}

public class BasicDemo {
    public static void main(String[] args) {
        System.out.println("begin");
        new Demo1().start();
        new Thread(new Demo2()).start();
        new Thread(() -> System.out.println("Demo3")).start();
        System.out.println("end");
    }
}
