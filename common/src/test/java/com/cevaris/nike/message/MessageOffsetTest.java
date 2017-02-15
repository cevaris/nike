package com.cevaris.nike.message;

import com.cevaris.nike.util.ByteBufferUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageOffsetTest {

  @Test
  public void testMessageOffsetSerialization() {
    byte[] key = "teyKey".getBytes();
    byte[] payload = "secretPayload".getBytes();
    Long offset = 100L;

    MessageOffset original = new MessageOffset(offset, Message.fromKeyPayload(key, payload));
    MessageOffset actual = MessageOffset.fromBytes(original.toBytes());

    assertEquals(original.toBytes(), actual.toBytes());
    assertEquals(original, actual);
    assertEquals(offset, actual.getOffset());
  }

  @Test
  public void testEmptyMessageOffsetSerialization() {
    byte[] key = "teyKey".getBytes();
    byte[] payload = ByteBufferUtils.emptyByteArray();
    Long offset = 100L;

    MessageOffset original = new MessageOffset(offset, Message.fromKeyPayload(key, payload));
    MessageOffset actual = MessageOffset.fromBytes(original.toBytes());

    assertEquals(original.toBytes(), actual.toBytes());
    assertEquals(original, actual);
    assertEquals(offset, actual.getOffset());
  }


}