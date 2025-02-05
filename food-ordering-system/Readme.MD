
TODO: set-up alternatives java, etc.

# Add maven wrapper
- mvn -N wrapper:wrapper -Dmaven=3.5.2

- In maven-wrapper.properties, change to distributionType to bin


## Generate dependencies
```
mvn com.github.ferstl:depgraph-maven-plugin:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=com.nttdata.food.ordering.system*:*"
```
