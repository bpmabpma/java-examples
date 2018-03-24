package tw.bill;

public class SleepDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("I am awake");
            }
        });
        thread.start();
        thread.interrupt();
    }
}
