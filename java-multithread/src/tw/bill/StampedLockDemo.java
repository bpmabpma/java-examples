package tw.bill;

import java.util.Arrays;
import java.util.concurrent.locks.*;

class ArrayList2<E> {
    private StampedLock lock = new StampedLock();
    private Object[] elems;
    private int next;

    public ArrayList2(int capacity) {
        elems = new Object[capacity];
    }

    public ArrayList2() {
        elems = new Object[16];
    }

    public void add(E element) {
        long stamp = lock.writeLock();
        try {
            if (next == elems.length) {
                elems = Arrays.copyOf(elems, elems.length * 2);
            }
            elems[next++] = element;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public E get(int index) {
        long stamp = lock.tryOptimisticRead();
        Object elem = elems[index];
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                elem = elems[index];
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return (E) elem;
    }

    public int size() {
        long stamp = lock.tryOptimisticRead();
        int size = next;
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                size = next;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return size;
    }
}

public class StampedLockDemo {

}