package com.cevaris.nike.client;

import java.util.Iterator;

public class ProvisionedClient<T> implements Client<T> {


    public void write(T payload) {
    }

    public Iterator<T> subscriber() {
        return null;
    }
}
