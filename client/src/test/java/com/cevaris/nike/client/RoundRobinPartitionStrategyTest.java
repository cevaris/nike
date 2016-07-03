package com.cevaris.nike.client;

import com.cevaris.nike.util.Data;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoundRobinPartitionStrategyTest {

    @Test
    public void testRoundRobinCycle() {
        int partitionSize = 10;
        PartitionStrategy strategy = new RoundRobinPartitionStrategy();

        List<Integer> expected = Data.intList(0, partitionSize);
        expected.addAll(expected);

        List<Integer> actual = Lists.newArrayList();

        for (Integer i = 0; i < partitionSize * 2; i++) {
            actual.add(strategy.getPartition(partitionSize, null));
        }

        assertEquals(expected, actual);
    }
}