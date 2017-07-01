package vincentg.util;

import com.beust.jcommander.IStringConverter;
import vincentg.dto.EnumObservatory.EnumDistance;

/**
 * @author vincentg
 */
public class DistanceConverter implements IStringConverter<EnumDistance> {
    @Override
    public EnumDistance convert(String value) {
        EnumDistance distanceUnit;
        try {
            distanceUnit = EnumDistance.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException iae) {
            distanceUnit = EnumDistance.M;
        }
        return distanceUnit;
    }
}
