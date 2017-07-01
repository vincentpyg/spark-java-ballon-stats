package vincentg.function;

import org.apache.spark.api.java.function.PairFunction;
import vincentg.dto.BallonLog;
import vincentg.dto.ObservatoryKey;
import scala.Tuple2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static vincentg.constants.BallonConstants.COORDINATES_DELIMITER;
import static vincentg.constants.BallonConstants.LOG_DELIMITER;

/**
 * @author vincentg
 */
public class ObservatoryKeyMapper implements PairFunction<String, ObservatoryKey, BallonLog> {

    @Override
    public Tuple2<ObservatoryKey, BallonLog> call(String s) throws Exception {

        ObservatoryKey okey = new ObservatoryKey();
        String[] logParts = s.split(LOG_DELIMITER);

        okey.setObservatory(logParts[3]);
        okey.setTimestamp(LocalDateTime.parse(logParts[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        String[] coordinates = logParts[1].split(COORDINATES_DELIMITER);

        BallonLog ballonLog = BallonLog.builder()
                .observer(logParts[3])
                .timestamp(LocalDateTime.parse(logParts[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .temp(Double.parseDouble(logParts[2]))
                .locationX(Integer.parseInt(coordinates[0]))
                .locationY(Integer.parseInt(coordinates[1]))
                .build();

        return new Tuple2<>(okey,ballonLog);
    }
}
