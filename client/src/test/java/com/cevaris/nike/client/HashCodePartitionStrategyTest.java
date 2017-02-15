package com.cevaris.nike.client;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashCodePartitionStrategyTest {

  @Test
  public void testHashCodeCycle() {
    int partitionSize = 8;
    PartitionStrategy strategy = new HashCodePartitionStrategy();

    List<Integer> expected = Lists.newArrayList(7, 5, 3, 4, 2, 6, 1, 6, 2, 0, 5, 1, 2, 5, 2, 1);
    List<Integer> actual = Lists.newArrayList();

    List<String> testObjects = Lists.newArrayList();
    for (int i = 0; i < partitionSize * 2; i++) {
      testObjects.add(DigestUtils.md5Hex(String.valueOf(i)));
    }

    for (String obj : testObjects) {
      actual.add(strategy.getPartition(partitionSize, obj));
    }

    assertEquals(expected, actual);
  }

}