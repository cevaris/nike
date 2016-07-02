package com.cevaris.nike.server;

import com.cevaris.nike.Hello;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class PartitionServer {
    private static Logger log = Logger.getLogger(PartitionServer.class);
    private static ServerConfig config = new ServerConfig("config/config.properties");

    public static void main(String[] args) {
        try {
            Integer thriftPort = config.getInt(ServerConfig.SERVER_THRIFT_PORT);
            TServerSocket serverTransport = new TServerSocket(thriftPort);

            Hello.Processor processor = new Hello.Processor<Hello.Iface>(new Hello.Iface() {
                public String hi() throws TException {
                    return "hellow";
                }
            });

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
