package com.cevaris.nike.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.google.common.base.Preconditions;

import org.apache.log4j.Logger;

public class FileMessageSet extends MessageSet {

  private Logger logger = Logger.getLogger(FileMessageSet.class);

  private final File file;
  private final FileChannel channel;
  private final Integer start;
  private final Integer end;
  private final Boolean isSlice;

  private final AtomicInteger currentSize;

  public FileMessageSet(File file, FileChannel channel, Integer start, Integer end, Boolean isSlice) throws IOException {
    Preconditions.checkNotNull(file);
    Preconditions.checkNotNull(channel);

    this.file = file;
    this.channel = channel;
    this.start = start;
    this.end = end;
    this.isSlice = isSlice;

    Integer currentSize = end - start;
    if (!this.isSlice) {
      channel.position(Math.min(Long.valueOf(channel.size()).intValue(), end));
      currentSize = Math.min(Long.valueOf(channel.size()).intValue(), end) - start;
    }
    this.currentSize = new AtomicInteger(currentSize);
  }

  public FileMessageSet read(Integer position, Integer size) throws IOException {
    return new FileMessageSet(
        file, channel,
        this.start + position, // start
        Math.min(this.start + position + size, currentByteSize()), // end
        true // isSlice
    );
  }

//    public Integer append(MessageSet messages) {
//        Integer written = messages.writeTo(channel, 0L, messages.currentByteSize());
//        logger.debug(String.format("wrote %d bytes to %s", written, file.getAbsolutePath()));
//        currentSize.getAndAdd(written);
//    }

  public FileMessageSet(File file, Integer start, Integer end, Boolean isSlice) throws IOException {
    this(file, FileMessageSet.openChannel(file, true), start, end, true);
  }

  @Override
  public Integer currentByteSize() {
    return currentSize.get();
  }

  @Override
  public Integer writeTo(GatheringByteChannel destChannel, Long offset, Integer maxSize) throws IOException {
    Long position = start + offset;
    Integer byteSize = Math.min(maxSize, currentByteSize());
    Long bytesWritten = channel.transferTo(position, byteSize, channel);
    return bytesWritten.intValue();
  }

  @Override
  public Iterator<MessageOffset> iterator() {
    return null;
  }

  @Override
  public void forEach(Consumer<? super MessageOffset> action) {

  }

  @Override
  public Spliterator<MessageOffset> spliterator() {
    return null;
  }

  private static FileChannel openChannel(File file, Boolean mutable) throws IOException {
    return openChannel(file, mutable, false, 0, false);
  }

  private static FileChannel openChannel(File file, Boolean mutable, Boolean fileAlreadyExists, Integer initFileSize, Boolean preallocate) throws IOException {
    if (mutable) {
      if (fileAlreadyExists)
        return new RandomAccessFile(file, "rw").getChannel();
      else {
        if (preallocate) {
          RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
          randomAccessFile.setLength(initFileSize);
          return randomAccessFile.getChannel();
        } else
          return new RandomAccessFile(file, "rw").getChannel();
      }
    } else
      return new FileInputStream(file).getChannel();
  }
}
