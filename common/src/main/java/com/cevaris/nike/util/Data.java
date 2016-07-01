package com.cevaris.nike.util;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

import java.util.Iterator;

public class Data {

    public static Iterator<Integer> intList(Integer start, Integer end) {
        return ContiguousSet.create(
                Range.closedOpen(start, end), DiscreteDomain.integers()
        ).iterator();
    }
}
