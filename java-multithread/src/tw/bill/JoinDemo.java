package tw.bill;

public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Thread A start"); // Use main thread to be thread A

        Thread threadB = new Thread(() -> {
            System.out.println("Thread B start");
            for(int i = 0; i < 5; i++) {
                System.out.println("Run Thread B...");
            }
            System.out.println("Thread B end");
        });
        threadB.start();
        threadB.join(1000);
        System.out.println("Thread A end");
    }
}
