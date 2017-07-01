package vincentg.function;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.Function;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static vincentg.constants.BallonConstants.COORDINATES_DELIMITER;

/**
 * @author vincentg
 */
public class InvalidLogFilter implements Function<String,Boolean> {

    private static final Logger LOG = Logger.getLogger(InvalidLogFilter.class.getName());

    @Override
    public Boolean call(String logEntry) throws Exception {
        String[] logEntryAr = logEntry.split("\\|");


        if (logEntryAr.length == 4) {
            try {
                validateTimeStamp(logEntryAr[0]);
                validateCoordinates(logEntryAr[1]);
                validateTemperature(logEntryAr[2]);
                validateObservatory(logEntryAr[3]);
                return true;
            } catch (IllegalArgumentException iae) {
                LOG.debug("Invalid Record: "+logEntry);
            }
        }
        return false;
    }

    private void validateTimeStamp(String timestamp) throws IllegalArgumentException {
        try {
            LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid timestamp");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void validateCoordinates(String coords) throws IllegalArgumentException {
        try {
            String[] coordinates = coords.split(COORDINATES_DELIMITER);
            Integer.parseInt(coordinates[0]);
            Integer.parseInt(coordinates[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid coordinates");
        }
    }

    private void validateObservatory(String observatory) throws IllegalArgumentException {
        Validate.notEmpty(observatory);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void validateTemperature(String temperature) throws IllegalArgumentException {
        try {
            Double.parseDouble(temperature);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid timestamp");
        }
    }
}
