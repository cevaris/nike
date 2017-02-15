package com.cevaris.nike.message;

public class TopicPartition {
  public final String topic;
  public final Integer paritition;

  public TopicPartition(String topic, Integer paritition) {
    this.topic = topic;
    this.paritition = paritition;
  }


}
