package com.cevaris.nike.server;

import com.cevaris.nike.message.Message;
import com.cevaris.nike.thrift.PartitionPublishRequest;
import com.cevaris.nike.thrift.PartitionPublishResponse;
import com.cevaris.nike.thrift.PartitionService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

public class PartitionServiceHandler implements PartitionService.Iface {

    private static Logger log = Logger.getLogger(PartitionServiceHandler.class);

    public PartitionPublishResponse publish(PartitionPublishRequest request) throws TException {

        Partition partition = null;//partitionLogs.get(request.partition);
        if (partition == null) {
            String message = String.format("Invalid partition: %d", request.partition);
            throw new TException(message);
        }

        Message message = Message.fromBytes(request.payload);

        log.info(String.format("accepting request: %d Thread: %s", request.hashCode(), Thread.currentThread().getName()));
        return null;
    }
}
