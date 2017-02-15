package com.cevaris.nike.examples;

import java.util.Iterator;

import com.cevaris.nike.client.ProvisionedPublisherClient;
import com.cevaris.nike.client.PublisherClient;
import com.cevaris.nike.util.Generate;

public class SimpleClient {

  public static void main(String[] args) {
    PublisherClient publisherClient = new ProvisionedPublisherClient.Builder().build();

    Iterator intIter = Generate.intIterator(0, 10);
    while (intIter.hasNext()) {
      publisherClient.write(intIter.next());
    }

    Iterator intSubscriber = publisherClient.subscriber();
    while (intSubscriber.hasNext()) {
      System.out.println(intSubscriber.next());
    }
  }
}
