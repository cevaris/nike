package com.cevaris.nike.client;

import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinPartitionStrategy implements PartitionStrategy {
  private final AtomicInteger currentPartition = new AtomicInteger(0);

  public Integer getPartition(Integer partitionSize, Object o) {
    synchronized (currentPartition) {
      if (currentPartition.intValue() < partitionSize - 1) {
        return currentPartition.getAndIncrement();
      } else {
        return currentPartition.getAndSet(0);
      }
    }
  }
}
