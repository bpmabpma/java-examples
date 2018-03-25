package tw.bill;

import java.util.concurrent.locks.*;

class Store2 {
    private int product = -1; // -1 is empty
    private Lock lock = new ReentrantLock();
    private Condition producerCond = lock.newCondition();
    private Condition consumerCond = lock.newCondition();

    public void setProduce(int produce) throws InterruptedException {
        lock.lock();
        try {
            waitIfFull();
            this.product = produce;
            System.out.printf("setProduct %d %n", this.product);
            consumerCond.signal();
        } finally {
            lock.unlock();
        }
    }

    private void waitIfFull() throws InterruptedException {
        while (this.product != -1) {
            producerCond.await();
        }
    }

    public int getProduce() throws InterruptedException {
        lock.lock();
        try {
            waitIfEmpty();
            int p = this.product;
            this.product = -1;
            System.out.printf("getProducet %d %n", p);
            producerCond.signal();
            return p;
        } finally {
            lock.unlock();
        }
    }

    private void waitIfEmpty() throws InterruptedException {
        while (this.product == -1) {
            consumerCond.await();
        }
    }
}

public class ConditionDemo {

}
