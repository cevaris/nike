package com.cevaris.nike.message;

import java.nio.ByteBuffer;
import java.util.Objects;

import com.cevaris.nike.util.ByteBufferUtils;
import com.google.common.base.Preconditions;

/**
 * Message Encoding
 * <pre><code>
 * 1. 4 byte CRC32 of the message
 * 2. 1 byte "magic" identifier to allow format changes, value is 0 or 1
 * 3. 1 byte "attributes" identifier to allow annotations on the message independent of the version
 *    bit 0 ~ 2 : Compression codec.
 *      0 : no compression
 *      1 : gzip
 *      2 : snappy
 *      3 : lz4
 *    bit 3 : Timestamp type
 *      0 : create time
 *      1 : log append time
 *    bit 4 ~ 7 : reserved
 * 4. (Optional) 8 byte timestamp only if "magic" identifier is greater than 0
 * 5. 4 byte key length, containing length K
 * 6. K byte key
 * 7. 4 byte payload length, containing length V
 * 8. V byte payload
 * </code></pre>
 */
public class Message {
  private final static Byte messageV0 = 1;
  private final static Byte DefaultMessageVersion = messageV0;

  private final static Integer VersionOffset = 0;
  private final static Integer VersionLen = 1;

  private final static Integer AttributesOffset = VersionOffset + VersionLen;
  private final static Integer AttributesLen = 8;

  private final static Integer LogAppendTimeOffset = AttributesOffset + AttributesLen;
  private final static Integer LogAppendTimeLen = 8;

  private final static Integer KeyLengthOffset = LogAppendTimeOffset + LogAppendTimeLen;
  private final static Integer KeyLengthLen = 4;
  private final static Integer PayloadLengthLen = 4;

  private ByteBuffer buffer;

  public Message(Byte version, Long attributes, Long logAppendTime, Integer keyLength, byte[] key, Integer payloadLength, byte[] payload) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(payload);

    buffer = ByteBuffer.allocate(
        KeyLengthOffset + (KeyLengthLen + PayloadLengthLen + keyLength + payloadLength)
    );

    buffer.put(version);
    buffer.putLong(attributes);
    buffer.putLong(logAppendTime);
    buffer.putInt(keyLength);
    buffer.put(key);
    buffer.putInt(payloadLength);
    buffer.put(payload);

    buffer.rewind();
  }

  public ByteBuffer toByteBuffer() {
    buffer.rewind();
    return buffer;
  }

  public static Message fromBytes(ByteBuffer bytes) {
    bytes.rewind();

    Byte version = bytes.get();
    Long attributes = bytes.getLong();
    Long logAppendTime = bytes.getLong();

    Integer keyLength = bytes.getInt();
    byte[] key = new byte[keyLength];
    bytes.get(key);

    Integer payloadLength = bytes.getInt();
    byte[] payload = new byte[payloadLength];
    bytes.get(payload);

    bytes.rewind();

    return new Message(
        version, attributes, logAppendTime, keyLength, key, payloadLength, payload
    );
  }

  public static Message fromKeyPayload(byte[] key, byte[] payload) {
    Byte version = DefaultMessageVersion;
    Long attributes = 0L;
    Long logAppendTime = 0L;
    Integer keyLength = 0;
    Integer payloadLength = 0;

    if (key != null) {
      keyLength = key.length;
    } else {
      key = ByteBufferUtils.emptyByteArray();
    }

    if (payload != null) {
      payloadLength = payload.length;
    } else {
      payload = ByteBufferUtils.emptyByteArray();
    }

    return new Message(
        version, attributes, logAppendTime, keyLength, key, payloadLength, payload
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(buffer);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Message other = (Message) obj;
    return Objects.equals(this.buffer, other.toByteBuffer());
  }
}
