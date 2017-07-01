package vincentg.function;

import org.apache.spark.api.java.function.Function;
import vincentg.dto.BallonLog;
import vincentg.dto.BallonStats;
import vincentg.dto.EnumObservatory.EnumTemperature;
import vincentg.dto.ObservatoryKey;
import scala.Tuple2;

/**
 * @author vincentg
 */
public class BallonStatsAgg implements Function<Tuple2<String,Iterable<Tuple2<ObservatoryKey, BallonLog>>>, BallonStats> {

    @Override
    public BallonStats call(Tuple2<String, Iterable<Tuple2<ObservatoryKey, BallonLog>>> v1) throws Exception {

        final BallonStats ballonStats = new BallonStats();
        ballonStats.setObservatory(v1._1());

        v1._2().forEach(v2 -> {
            BallonLog ballonLog = v2._2();
            ballonStats.addTemp(
                    normalizeTemp(ballonStats.getObservatoryUnit().getTemperature(),
                    ballonLog.getTemp()));
            ballonStats.addLocation(ballonLog.getLocationX(), ballonLog.getLocationY());
            ballonStats.incrementObservations();
        });

        return ballonStats;
    }

    private double normalizeTemp(EnumTemperature baseTempUnit, double temp) {
        double tempInCelsious = temp;
        switch (baseTempUnit) {
            case FAHRENHEIT:
                tempInCelsious = (temp-32)*5/9; //todo: add to constants
                break;
            case KELVIN:
                tempInCelsious = temp-273.15; //todo: add to constants
                break;
        }
        return tempInCelsious;
    }
}
