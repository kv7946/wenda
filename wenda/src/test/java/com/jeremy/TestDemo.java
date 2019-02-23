package com.jeremy;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();
        ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
        lock1.writeLock();
        Condition condition = lock.newCondition();
        Unsafe unsafe = null;
        unsafe.compareAndSwapInt(1,2,3,4);
        HashMap<String,String> map = new HashMap<>();

    }
}
