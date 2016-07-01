package com.cevaris.nike.examples;

import com.cevaris.nike.client.Client;
import com.cevaris.nike.client.ProvisionedClient;
import com.cevaris.nike.util.Data;

import java.util.Iterator;

public class SimpleClient {

    public static void main(String[] args) {
        Client<Integer> client = new ProvisionedClient<Integer>();

        Iterator<Integer> intIter = Data.intList(0, 10);
        while (intIter.hasNext()) {
            client.write(intIter.next());
        }

        Iterator<Integer> intSubscriber = client.subscriber();
        while (intSubscriber.hasNext()) {
            System.out.println(intSubscriber.next());
        }
    }
}
