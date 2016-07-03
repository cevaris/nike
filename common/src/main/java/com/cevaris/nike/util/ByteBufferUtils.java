package com.cevaris.nike.util;


import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ByteBufferUtils {

    public static byte[] emptyByteArray() {
        return new byte[0];
    }

    public static ByteBuffer emptyBuffer() {
        return ByteBuffer.allocate(0);
    }

    public static Integer crc32(byte[] bytes, Integer offset, Integer length) {
        Checksum checksum = new CRC32();
        if (bytes != null) {
            checksum.update(bytes, offset, length);
            return Long.valueOf(checksum.getValue()).intValue();
        } else {
            return 0;
        }
    }

    public static Integer crc32(ByteBuffer bb) {
        Checksum checksum = new CRC32();
        if (bb.hasArray()) {
            bb.rewind();
            checksum.update(bb.array(), 0, bb.array().length);
            return Long.valueOf(checksum.getValue()).intValue();
        } else {
            return 0;
        }

    }
}
