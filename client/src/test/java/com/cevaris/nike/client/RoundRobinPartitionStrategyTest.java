package com.cevaris.nike.client;

import java.util.List;

import com.cevaris.nike.util.Generate;
import com.google.common.collect.Lists;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoundRobinPartitionStrategyTest {

  @Test
  public void testRoundRobinCycle() {
    int partitionSize = 8;
    PartitionStrategy strategy = new RoundRobinPartitionStrategy();

    // Expect to see iterate list in order two times
    List<Integer> expected = Lists.newArrayList();
    expected.addAll(Generate.intList(0, partitionSize));
    expected.addAll(Generate.intList(0, partitionSize));

    List<Integer> actual = Lists.newArrayList();

    // assert we cycled the partition list twice
    for (Integer i = 0; i < partitionSize * 2; i++) {
      actual.add(strategy.getPartition(partitionSize, null));
    }

    assertEquals(expected, actual);
  }
}