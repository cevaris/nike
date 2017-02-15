package com.cevaris.nike.util;

public class MathUtils {

  public static Integer roundToExactMultiple(Integer number, Integer factor) {
    return factor * (number / factor);
  }
}
