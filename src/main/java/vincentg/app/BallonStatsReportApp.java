package vincentg.app;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import vincentg.dto.BallonLog;
import vincentg.dto.EnumObservatory.*;
import vincentg.dto.ObservatoryKey;
import vincentg.function.BallonStatsAgg;
import vincentg.function.InvalidLogFilter;
import vincentg.function.ObservatoryKeyMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author vincentg
 */
public class BallonStatsReportApp implements Serializable {

    private final String inputFile;
    private final String outputFile;
    private final boolean local;

    public BallonStatsReportApp(String inputFile,
                                String outputFile,
                                boolean local) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.local = local;
    }

    public void run(EnumDistance distUnit, EnumTemperature tempUnit) {

        SparkConf conf = new SparkConf().setAppName("ballon-stats");

        if (local)
            conf.setMaster("local");

        JavaSparkContext ctx = new JavaSparkContext(conf);

        JavaPairRDD<ObservatoryKey, BallonLog> logRdd = ctx
                .textFile(inputFile)
                .filter(new InvalidLogFilter())
                .mapToPair(new ObservatoryKeyMapper());

        logRdd.sortByKey(false)
                .groupBy( logEntry -> logEntry._1().getObservatory()) //todo: improve. not very efficient
                .map(new BallonStatsAgg())
                .repartition(1)
                .foreachPartition(ballonStatsIterator -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
                        ballonStatsIterator.forEachRemaining(ballonStats -> {
                            try {
                                writer.write(ballonStats.createOutputEntry(distUnit, tempUnit)
                                        +System.lineSeparator());
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        ctx.stop();
    }

}
