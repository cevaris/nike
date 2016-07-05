package com.cevaris.nike.server;

import com.cevaris.nike.message.TopicPartition;
import com.cevaris.nike.thrift.PartitionService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.File;
import java.util.Map;

public class Broker {
    private static Logger log = Logger.getLogger(Broker.class);

    private final BrokerConfig config;

    public Broker() {
        config = new BrokerConfig("config/config.properties");
    }

    public static void main(String[] args) {
        Broker broker = new Broker();
        broker.listen();
    }

    private void listen() {
        try {
            Integer thriftPort = config.getInt(BrokerConfig.SERVER_THRIFT_PORT);
            TServerSocket serverTransport = new TServerSocket(thriftPort);

            PartitionService.Processor processor = new PartitionService.Processor<PartitionService.Iface>(
                    new PartitionServiceHandler()
            );

            TServer server = new TThreadPoolServer(
                    new TThreadPoolServer.Args(serverTransport).processor(processor)
            );

            log.info(String.format("Starting server on port %d", thriftPort));
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }


}
