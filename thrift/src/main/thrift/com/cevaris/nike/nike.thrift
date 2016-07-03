namespace java com.cevaris.nike.thrift

struct MessageEnvelope {
    1: string topic,
    2: i32 partition,
    3: binary payload,
    4: optional i64 diskOffset,
    5: optional i64 writtenAtMs,
}

struct PartitionPublishRequest {
    1: string topic,
    2: binary payload,
}

struct PartitionPublishResponse {
}

service PartitionService {
  PartitionPublishResponse publish(PartitionPublishRequest request);
}
