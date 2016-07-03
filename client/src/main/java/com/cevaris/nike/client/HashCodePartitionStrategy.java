package com.cevaris.nike.client;

public class HashCodePartitionStrategy implements PartitionStrategy {
    public Integer getPartition(Integer partitionSize, Object o) {
        return Math.abs(o.hashCode() % partitionSize);
    }
}
