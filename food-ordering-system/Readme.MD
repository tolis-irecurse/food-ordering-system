
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
 - chown -R 1000:1000 ./volumes (for local data dir)

### docker-compose
To start kafka cluster as a daemon:
 - docker-compose -f common.yml -f kafka_cluster.yml up -d

To initialize the kafka topics:
 - docker-compose -f common.yml -f init_kafka.yml up

To stop the kafka cluster:
 - docker-compose -f common.yml -f kafka_cluster.yml down -v
