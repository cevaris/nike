package com.cevaris.nike.client;

import java.util.Iterator;

public interface Client<T> {

    public void write(T payload);

    public Iterator<T> subscriber();

}
