package com.cevaris.nike.util;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Data {

    public static Iterator<Integer> intIterator(Integer start, Integer end) {
        return ContiguousSet.create(
                Range.closedOpen(start, end), DiscreteDomain.integers()
        ).iterator();
    }

    public static List<Integer> intList(Integer start, Integer end) {
        return Lists.newArrayList(intIterator(start, end));
    }

}
