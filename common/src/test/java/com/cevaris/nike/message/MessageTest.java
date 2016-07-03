package com.cevaris.nike.message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void testMessageSerialization() {
        byte[] key = "teyKey".getBytes();
        byte[] payload = "secretPayload".getBytes();

        Message original = Message.fromKeyPayload(key, payload);
        Message actual = original.fromBytes(original.toBytes());

        assertEquals(original.toBytes(), actual.toBytes());
    }

}