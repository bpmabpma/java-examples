package tw.bill;

import java.util.concurrent.locks.ReentrantLock;

class Resource {
    private ReentrantLock lock = new ReentrantLock();
    private String name;

    Resource(String name) {
        this.name = name;
    }

    void cooperate(Resource res) {
        while (true) {
            try {
                if (lockMeAnd(res)) {
                    System.out.printf("this resoure: %s, other resoures: %s%n", this.name, res.name);
                    break;
                }
            } finally {
                unLockMeAnd(res);
            }
        }
    }



    private boolean lockMeAnd(Resource res) {
        // using this will cause deadlock
//        this.lock.lock();
//        res.lock.lock();
//        return true;
        return this.lock.tryLock() && res.lock.tryLock();
    }

    private void unLockMeAnd(Resource res) {
        if (this.lock.isHeldByCurrentThread()) this.lock.unlock();
        if (res.lock.isHeldByCurrentThread()) res.lock.unlock();
    }
}

public class LockDemo {
    public static void main(String[] args) {
        Resource res1 = new Resource("Resource 1");
        Resource res2 = new Resource("Resource 2");

        Thread thread1 = new Thread( () -> {
           for (int i = 0; i < 10000; i++) {
               res1.cooperate(res2);
           }
        });

        Thread thread2 = new Thread ( () -> {
            for (int i = 0; i < 10000; i++) {
                res2.cooperate(res1);
            }
        });

        thread1.start();
        thread2.start();
    }
}
