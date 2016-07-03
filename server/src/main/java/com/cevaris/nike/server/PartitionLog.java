package com.cevaris.nike.server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * https://examples.javacodegeeks.com/core-java/util/concurrent/locks-concurrent/readwritelock/java-readwritelock-example/
 * http://stackoverflow.com/questions/9671126/how-to-read-a-file-from-a-certain-offset
 */
public class PartitionLog {
    private BrokerConfig config = null;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public PartitionLog(BrokerConfig config, String partitionLogPath) {
        this.config = config;
    }


}
