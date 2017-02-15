package com.cevaris.nike.message;

import java.nio.ByteBuffer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {

  @Test
  public void testMessageSerialization() {
    byte[] key = "teyKey".getBytes();
    byte[] payload = "myPayload".getBytes();

    Message expected = Message.fromKeyPayload(key, payload);
    ByteBuffer serializedMessage = expected.toByteBuffer();

    Message actual = Message.fromBytes(serializedMessage);

    assertEquals(expected.toByteBuffer(), actual.toByteBuffer());
    assertEquals(expected, actual);
  }

}