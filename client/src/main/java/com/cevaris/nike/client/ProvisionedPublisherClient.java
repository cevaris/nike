package com.cevaris.nike.client;

import com.cevaris.nike.thrift.PartitionService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * http://stackoverflow.com/questions/328496/when-would-you-use-the-builder-pattern
 */
public class ProvisionedPublisherClient implements PublisherClient {

    private static Logger log = Logger.getLogger(ProvisionedPublisherClient.class);

    private List<String> brokers;
    private String topicName;
    private Map<Integer, PartitionService.Client> partitionClients;

    public ProvisionedPublisherClient(ProvisionedPublisherClient.Builder builder) {
        this.brokers = builder.brokers;
        this.topicName = builder.topicName;
    }

    public void write(ByteBuffer key, ByteBuffer payload) {

    }

    private Map<Integer, PartitionService.Client> setupParitionClients(List<String> brokers) {
        Map<Integer, PartitionService.Client> clients = Maps.newConcurrentMap();

        for (String broker : brokers) {
            try {
                // TODO: setup hashed based partitionClients pool based off some shard
                String[] first = broker.split(":");
                String host = first[0];
                Integer port = Integer.parseInt(first[1]);

                TTransport transport = new TSocket(host, port);
                transport.open();

                TProtocol protocol = new TBinaryProtocol(transport);
                this.partitionClients.put(broker);
            } catch (TException ex) {
                log.error("failed connecting to broker", ex);
            }
        }


        return clients;
    }

    static class Builder {

        private List<String> brokers = Collections.singletonList("localhost:7321");
        private PartitionStrategy partitionStrategy = new HashCodePartitionStrategy();
        private String topicName;

        public Builder brokers(List<String> brokers) {
            this.brokers = brokers;
            return this;
        }

        public Builder topicName(String topicName) {
            this.topicName = topicName;
            return this;
        }

        public Builder partitionStrategy(PartitionStrategy partitionStrategy) {
            this.partitionStrategy = partitionStrategy;
            return this;
        }

        public PublisherClient build() {
            if (this.partitionStrategy == null)
                throw new IllegalArgumentException("missing patition strategy");
            if (this.topicName == null)
                throw new IllegalArgumentException("missing topic name");
            if (this.brokers == null || this.brokers.isEmpty())
                throw new IllegalArgumentException("missing broker host:ports");
            return new ProvisionedPublisherClient(this);
        }
    }
}
