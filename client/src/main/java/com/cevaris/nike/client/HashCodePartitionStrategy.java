package com.cevaris.nike.client;

public class HashCodePartitionStrategy<T> implements PartitionStrategy<T> {
    public Integer getPartition(Integer partitionSize, T o) {
        return Math.abs(o.hashCode() % partitionSize);
    }
}
