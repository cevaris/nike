package com.cevaris.nike.client;

interface PartitionStrategy {

    Integer getPartition(Integer partitionSize, Object o);

}
