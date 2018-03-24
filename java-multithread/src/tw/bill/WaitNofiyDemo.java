package tw.bill;

class Producer implements Runnable {
    private Store store;

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        System.out.println("produce start working...");
        for (int i = 1; i <= 10; i++) {
            try {
                store.setProduce(i);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("produce finish working...");
    }
}

class Consumer implements Runnable {
    private Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        System.out.println("consumer start working...");
        for (int i = 1; i <= 10; i++) {
            try {
                store.getProduce();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("consumer finish working...");
    }
}

class Store {
    private int product = -1; // -1 is empty

    public synchronized void setProduce(int produce) throws InterruptedException {
        waitIfFull();
        this.product = produce;
        System.out.printf("setProduct %d %n", this.product);
        notify();
    }

    private synchronized void waitIfFull() throws InterruptedException {
        while (this.product != -1) {
            wait();
        }
    }

    public synchronized int getProduce() throws InterruptedException {
        waitIfEmpty();
        int p = this.product;
        this.product = -1;
        System.out.printf("getProducet %d %n", p);
        notify();
        return p;
    }

    private synchronized void waitIfEmpty() throws InterruptedException {
        while (this.product == -1) {
            wait();
        }
    }
}

public class WaitNofiyDemo {
    public static void main(String[] args) throws InterruptedException {
        Store store = new Store();
        new Thread(new Producer(store)).start();
        new Thread(new Consumer(store)).start();
        Thread.sleep(10 * 1000);
    }
}
