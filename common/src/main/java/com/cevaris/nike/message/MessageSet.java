package com.cevaris.nike.message;

import java.io.IOException;
import java.nio.channels.GatheringByteChannel;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

abstract class MessageSet implements Iterable<MessageOffset> {

    protected final static Integer MessageSizeLength = 4;
    protected final static Integer OffsetLength = 8;
    protected final static Integer LogOverhead = MessageSizeLength + OffsetLength;

    abstract public Integer writeTo(GatheringByteChannel channel, Long offset, Integer maxSize) throws IOException;

    abstract public Iterator<MessageOffset> iterator();

    abstract public void forEach(Consumer<? super MessageOffset> action);

    abstract public Spliterator<MessageOffset> spliterator();

    abstract public Integer currentByteSize();

}
