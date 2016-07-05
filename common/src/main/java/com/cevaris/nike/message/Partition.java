package com.cevaris.nike.message;

public class Partition {
    public final String value;

    public Partition(String value) {
        this.value = value;
    }

    public static Partition valueOf(String value) {
        return new Partition(value);
    }
}