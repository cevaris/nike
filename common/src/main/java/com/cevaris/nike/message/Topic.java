package com.cevaris.nike.message;

public class Topic {
    public final String value;

    public Topic(String value) {
        this.value = value;
    }

    public static Topic valueOf(String name) {
        return new Topic(name);
    }
}
