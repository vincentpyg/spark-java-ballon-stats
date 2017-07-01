
## spark-java-ballon-stats

| switch | value |
|---|---|
|`-i`| input file/directory |
|`-o`| output file|
|`--local`| run in local mode|
|`-t`| temperature unit. possible values: `celsius`, `fahrenheit`, `kelvin`|
|`-d`| distance unit. posible values: `m`, `km`, `miles`|

### Building the jar for local run
```$xslt
gradlew build jar
```

### Building the jar for cluster run
```$xslt
gradlew build sparkJar
```

### Running the application in local mode
```#bash
java -jar spark-java-ballon-stats-1.0-SNAPSHOT.jar -i <input_file> -o <output_file> -t <temperature_unit> -d <distance_unit> --local
```


### Submitting the application to a spark cluster
```$xslt
spark-submit --class vincentg.Main --master <spark_master> spark-java-ballon-stats-1.0-SNAPSHOT.jar -i <input_file> -o <output_file> -t <temperature_unit> -d <distance_unit> 
```
