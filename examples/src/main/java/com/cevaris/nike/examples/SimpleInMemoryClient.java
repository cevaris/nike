package com.cevaris.nike.examples;

import java.util.Iterator;

import com.cevaris.nike.client.InMemoryPublisherClient;
import com.cevaris.nike.client.PublisherClient;
import com.cevaris.nike.util.Generate;

public class SimpleInMemoryClient {

  public static void main(String[] args) {
    PublisherClient<Integer> publisherClient = new InMemoryPublisherClient<Integer>();

    Iterator<Integer> intIter = Generate.intIterator(0, 10);
    while (intIter.hasNext()) {
      publisherClient.write(intIter.next());
    }

    Iterator<Integer> intSubscriber = publisherClient.subscriber();
    while (intSubscriber.hasNext()) {
      System.out.println(intSubscriber.next());
    }
  }
}
