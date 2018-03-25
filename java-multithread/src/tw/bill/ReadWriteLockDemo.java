package tw.bill;

import java.util.Arrays;
import java.util.concurrent.locks.*;

public class ReadWriteLockDemo {
    public static void main(String[] args) {

    }
}

class ArrayList<E> {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Object[] elems;
    private int next;

    public ArrayList(int capacity) {
        elems = new Object[capacity];
    }

    public ArrayList() {
        elems = new Object[16];
    }

    public void add(E element) {
        lock.writeLock().lock();
        try {
            if (next == elems.length) {
                elems = Arrays.copyOf(elems, elems.length * 2);
            }
            elems[next++] = element;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public E get(int index) {
        lock.readLock().lock();
        try {
            return (E) elems[index];
        } finally {
            lock.readLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return next;
        } finally {
            lock.readLock().unlock();
        }
    }
}


