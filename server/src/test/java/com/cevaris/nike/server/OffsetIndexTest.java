package com.cevaris.nike.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.cevaris.nike.util.Generate;
import com.google.common.collect.Lists;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OffsetIndexTest {

  @Test
  public void testOffsetIndexEmpty() throws IOException {
    Long baseOffset = 100L;
    File tmpIndex = Generate.tmpIndexFile(baseOffset);
    OffsetPosition expected = new OffsetPosition(baseOffset, 0);
    OffsetIndex index = new OffsetIndex(tmpIndex, baseOffset);

    OffsetPosition actual = index.lookup(baseOffset + 1);

    assertEquals(expected, actual);
  }

  @Test
  public void testOffsetIndexSingleWriteRead() throws IOException {
    Long baseOffset = 100L;
    File tmpIndex = Generate.tmpIndexFile(baseOffset);
    OffsetPosition expected = new OffsetPosition(baseOffset, 12);

    OffsetIndex index = new OffsetIndex(tmpIndex, baseOffset);
    index.append(expected.offset, expected.position);

    OffsetPosition actual = index.lookup(expected.offset);

    assertEquals(expected, actual);
  }

  @Test
  public void testOffsetIndexMultipleWriteRead() throws IOException {
    Long baseOffset = 100L;
    File tmpIndex = Generate.tmpIndexFile(baseOffset);
    OffsetIndex index = new OffsetIndex(tmpIndex, baseOffset);

    List<OffsetPosition> expected = Lists.newArrayList(
        new OffsetPosition(baseOffset, 10),
        new OffsetPosition(baseOffset + 1, 20),
        new OffsetPosition(baseOffset + 2, 30)
    );

    for (OffsetPosition o : expected) {
      index.append(o.offset, o.position);
    }

    for (OffsetPosition o : expected) {
      OffsetPosition actual = index.lookup(o.offset);
      assertEquals(o, actual);
    }
  }

}