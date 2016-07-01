package com.cevaris.nike.client;

import com.cevaris.nike.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.Iterator;

public class ProvisionedClient<T> implements Client<T> {


    public void write(T payload) {
        try {
            TServerSocket serverTransport;
            serverTransport = new TServerSocket(7911);
            Hello.Processor processor = new Hello.Processor(new Hello.Iface() {
                public String hi() throws TException {
                    return "hellow";
                }
            });
            TServer server = new TThreadPoolServer(
                    new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Starting server on port 7911 ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }

    public Iterator<T> subscriber() {
        return null;
    }
}
