package com.cevaris.nike.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.cevaris.nike.message.FileSuffix;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

public class Generate {

  public static Iterator<Integer> intIterator(Integer start, Integer end) {
    return ContiguousSet.create(
        Range.closedOpen(start, end), DiscreteDomain.integers()
    ).iterator();
  }

  public static List<Integer> intList(Integer start, Integer end) {
    return Lists.newArrayList(intIterator(start, end));
  }

  public static File lazyOffsetFile(String parent, Long offset, String suffix) {
    return new File(String.format("%s/%d%s", parent, offset, suffix));
  }

  @VisibleForTesting
  public static File tmpOffsetFile(Long offset, String suffix) {
    File file = lazyOffsetFile("/tmp", offset, suffix);
    if (file.exists()) {
      file.delete();
    }
    file.deleteOnExit();
    return file;
  }

  @VisibleForTesting
  public static File tmpIndexFile(Long offset) {
    return tmpOffsetFile(offset, FileSuffix.Index);
  }

  @VisibleForTesting
  public static File tmpSegmentFile(Long offset) {
    return tmpOffsetFile(offset, FileSuffix.PartitionData);
  }

}
