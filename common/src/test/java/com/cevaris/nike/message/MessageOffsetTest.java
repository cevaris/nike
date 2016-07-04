package com.cevaris.nike.message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageOffsetTest {

    @Test
    public void testMessageSerialization() {
        byte[] key = "teyKey".getBytes();
        byte[] payload = "secretPayload".getBytes();
        long offset = 100L;

        MessageOffset original = new MessageOffset(offset, Message.fromKeyPayload(key, payload));
        MessageOffset actual = MessageOffset.fromBytes(original.toBytes());

        assertEquals(original.toBytes(), actual.toBytes());
        assertEquals(original, actual);
    }


}