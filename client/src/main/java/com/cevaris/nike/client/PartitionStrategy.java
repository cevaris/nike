package com.cevaris.nike.client;

interface PartitionStrategy<K> {

    Integer getPartition(Integer partitionSize, K o);

}
