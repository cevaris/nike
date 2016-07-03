package com.cevaris.nike.client;

import java.nio.ByteBuffer;

public interface PublisherClient {

    void write(ByteBuffer key, ByteBuffer payload);

}
