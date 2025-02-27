
TODO: set-up alternatives java, etc.

# Add maven wrapper
- mvn -N wrapper:wrapper -Dmaven=3.5.2

- In maven-wrapper.properties, change to distributionType to bin


## Generate dependencies
```
mvn com.github.ferstl:depgraph-maven-plugin:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=com.nttdata.food.ordering.system*:*"
```

## Kafka

### Configuration
 - sudo chown -R 1000:1000 ./volumes (for local data dir)

### Kafka docker-compose
To start kafka cluster as a daemon:
 - docker-compose -f common.yml -f kafka_cluster.yml up -d

To initialize the kafka topics:
 - docker-compose -f common.yml -f init_kafka.yml up

To stop the kafka cluster:
 - docker-compose -f common.yml -f kafka_cluster.yml down -v

### kafkacat command
 - kcat -C -b localhost:19092 -t payment-request

## POSTGRES
- TBD

## Build docker-image with maven plugin
 - For Java 21 I had to set the Java version to 21
 - I also had to disable the spring cloud bindings