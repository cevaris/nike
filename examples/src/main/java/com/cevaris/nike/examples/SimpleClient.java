package com.cevaris.nike.examples;

import com.cevaris.nike.client.PublisherClient;
import com.cevaris.nike.client.ProvisionedPublisherClient;
import com.cevaris.nike.util.Data;

import java.util.Iterator;

public class SimpleClient {

    public static void main(String[] args) {
        PublisherClient publisherClient = new ProvisionedPublisherClient.Builder().build();

        Iterator intIter = Data.intIterator(0, 10);
        while (intIter.hasNext()) {
            publisherClient.write(intIter.next());
        }

        Iterator intSubscriber = publisherClient.subscriber();
        while (intSubscriber.hasNext()) {
            System.out.println(intSubscriber.next());
        }
    }
}
