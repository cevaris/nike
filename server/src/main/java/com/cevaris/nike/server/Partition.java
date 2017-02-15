package com.cevaris.nike.server;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.cevaris.nike.message.Message;
import com.cevaris.nike.message.MessageOffset;

/**
 * https://examples.javacodegeeks.com/core-java/util/concurrent/locks-concurrent/readwritelock/java-readwritelock-example/
 * http://stackoverflow.com/questions/9671126/how-to-read-a-file-from-a-certain-offset
 */
public class Partition {
  private final BrokerConfig config;
  private final ConcurrentNavigableMap<Long, PartitionSegment> segments;

  public Partition(BrokerConfig config) {
    this.config = config;
    this.segments = loadSegments();
  }

  private ConcurrentNavigableMap<Long, PartitionSegment> loadSegments() {
    ConcurrentNavigableMap<Long, PartitionSegment> loadedSegments = new ConcurrentSkipListMap<Long, PartitionSegment>();
    return loadedSegments;
  }

  public MessageOffset append(Message message) {
    return null;
  }

}
