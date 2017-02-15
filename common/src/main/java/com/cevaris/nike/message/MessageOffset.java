package com.cevaris.nike.message;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * <pre><code>
 * offset         : 8 bytes
 * message length : 4 bytes (value: 4 + 1 + 1 + 8 + 4 + K + 4 + V)
 * version        : 1 byte
 * attributes     : 1 byte
 * timestamp      : 8 bytes (log append time)
 * key length     : 4 bytes
 * key            : K bytes
 * value length   : 4 bytes
 * value          : V bytes
 * </code></pre>
 */
public class MessageOffset {

    /**
     * offset of message in segment file
     */
    private final static Integer OffsetOffset = 0;
    private final static Integer OffsetLen = 8;

    /**
     * number of bytes of full message
     */
    private final static Integer MessageLengthOffset = OffsetOffset + OffsetLen;
    private final static Integer MessageLengthLen = 4;

    private ByteBuffer buffer;
    private Long offset;

    public MessageOffset(Long offset, Message message) {
        Preconditions.checkNotNull(offset);
        Preconditions.checkNotNull(message);

        this.offset = offset;

        Integer messageLength = message.toBytes().array().length;

        this.buffer = ByteBuffer.allocate(
                OffsetLen + MessageLengthLen + messageLength
        );

        this.buffer.putLong(offset);
        this.buffer.putInt(messageLength);
        this.buffer.put(message.toBytes());
    }

    public Long getOffset() {
        return offset;
    }

    public ByteBuffer toBytes() {
        buffer.rewind();
        return buffer;
    }

    public static MessageOffset fromBytes(ByteBuffer bytes) {
        Preconditions.checkNotNull(bytes);

        Long offset = bytes.getLong();
        Integer messageLength = bytes.getInt();

        byte[] messageBytes = new byte[messageLength];
        bytes.get(messageBytes);
        Message message = Message.fromBytes(ByteBuffer.wrap(messageBytes));

        return new MessageOffset(offset, message);
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
        final MessageOffset other = (MessageOffset) obj;
        return Objects.equals(this.buffer, other.toBytes());
    }
}
