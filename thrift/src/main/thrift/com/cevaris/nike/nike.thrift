namespace java com.cevaris.nike.thrift

struct PartitionPublishRequest {
    1: string topic,
    2: i32 partition,
    3: binary payload,
}

struct PartitionPublishResponse {
}

service PartitionService {
  PartitionPublishResponse publish(PartitionPublishRequest request);
}
