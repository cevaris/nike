#!/usr/bin/env bash -e

mvn install -DskipTests
mvn exec:java -X \
    -pl server \
    -Dexec.mainClass=com.cevaris.nike.server.PartitionServer
