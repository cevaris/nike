package com.cevaris.nike.util;

import java.nio.ByteBuffer;

public interface ByteSerializable<T> {

    ByteBuffer toBytes();

    T fromBytes(ByteBuffer bytes);

}
