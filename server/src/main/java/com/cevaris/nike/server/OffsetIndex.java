package com.cevaris.nike.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import com.cevaris.nike.util.MathUtils;
import com.cevaris.nike.util.Memory;

import org.apache.log4j.Logger;

public class OffsetIndex {

  private Integer MaxIndexSize = 100 * Memory.Megabyte;

  private Logger logger = Logger.getLogger(OffsetIndex.class);

  private final Long baseOffset;

  private volatile MappedByteBuffer vMbb;
  private volatile Integer vEntries;

  public OffsetIndex(File indexFile, Long baseOffset) {
    this.baseOffset = baseOffset;
    this.vMbb = buildMemoryMap(indexFile);
    this.vEntries = this.vMbb.position() / Memory.Byte;
  }

  public void append(Long newOffset, Integer newPosition) {
    vMbb.putInt((int) (newOffset - baseOffset));
    vMbb.putInt(newPosition);
    this.vEntries += 1;
  }

  public OffsetPosition lookup(Long targetOffset) {
    ByteBuffer buffer = vMbb.duplicate();
    Integer indexPosition = searchForOffset(buffer, targetOffset, this.baseOffset, vEntries);
    if (indexPosition == -1) {
      return new OffsetPosition(baseOffset, 0);
    } else {
      return new OffsetPosition(baseOffset + relativeOffset(buffer, indexPosition), physicalOffset(buffer, indexPosition));
    }
  }

  private Integer relativeOffset(ByteBuffer buffer, Integer offset) {
    return buffer.getInt(offset * Memory.Byte);
  }

  private Integer physicalOffset(ByteBuffer buffer, Integer offset) {
    return buffer.getInt((offset * Memory.Byte) + (Memory.Integer / Memory.Byte));
  }

  private Integer searchForOffset(ByteBuffer buffer, Long targetOffset, Long baseOffset, Integer entries) {
    // we only store the difference from the base offset so calculate that
    Integer relOffset = (int) (targetOffset - baseOffset);

    // check if the index is empty
    if (entries == 0)
      return -1;

    // binary search for the entry
    Integer lo = 0;
    Integer hi = entries - 1;
    while (lo < hi) {
      Integer mid = Double.valueOf(Math.ceil(hi / 2.0 + lo / 2.0)).intValue();
      Integer found = relativeOffset(buffer, mid);
      if (found.equals(relOffset))
        return mid;
      else if (found < relOffset)
        lo = mid;
      else
        hi = mid - 1;
    }
    return lo;
  }

  private MappedByteBuffer buildMemoryMap(File indexFile) {
    RandomAccessFile raf = null;
    MappedByteBuffer mappedBuffer = null;

    try {
      Boolean emptyFile = indexFile.createNewFile();
      raf = new RandomAccessFile(indexFile, "rw");

      Integer rafLen = MathUtils.roundToExactMultiple(MaxIndexSize, Memory.Byte);

      if (emptyFile) {
        raf.setLength(rafLen);
      }

      mappedBuffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, rafLen);

      if (emptyFile) {
        mappedBuffer.position(0);
      } else {
        mappedBuffer.position(MathUtils.roundToExactMultiple(mappedBuffer.limit(), Memory.Byte));
      }

    } catch (IOException e) {
      logger.error("failed creating memory mapped index file", e);
    } finally {

      try {
        if (raf != null) {
          raf.close();
        }
      } catch (IOException e) {
        logger.error("failed closing raf index file", e);
      }

    }

    return mappedBuffer;
  }
}
