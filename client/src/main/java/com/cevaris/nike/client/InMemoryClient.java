package com.cevaris.nike.client;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryClient<T> implements Client<T> {

    private Queue<T> queue = new ConcurrentLinkedQueue<T>();

    public void write(T payload) {
        queue.add(payload);
    }

    public Iterator<T> subscriber() {
        return queue.iterator();
    }
}
